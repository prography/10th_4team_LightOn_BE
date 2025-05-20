package com.prography.lighton.member.application;

import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.common.domain.vo.RegionInfo;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.member.domain.entity.TemporaryMember;
import com.prography.lighton.member.domain.entity.vo.MarketingAgreement;
import com.prography.lighton.member.domain.entity.vo.Phone;
import com.prography.lighton.member.exception.DuplicateMemberException;
import com.prography.lighton.member.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.infrastructure.repository.TemporaryMemberRepository;
import com.prography.lighton.member.presentation.dto.request.CompleteMemberProfileRequestDTO;
import com.prography.lighton.member.presentation.dto.response.CompleteMemberProfileResponseDTO;
import com.prography.lighton.region.infrastructure.cache.RegionCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompleteMemberProfileService implements CompleteMemberProfileUseCase {

    private final TemporaryMemberRepository temporaryMemberRepository;
    private final MemberRepository memberRepository;

    private final RegionCache regionCache;
    private final TokenProvider tokenProvider;

    @Override
    public CompleteMemberProfileResponseDTO completeMemberProfile(final Long temporaryMemberId,
                                                                  final CompleteMemberProfileRequestDTO request) {
        TemporaryMember temporaryMember = getTemporaryMember(temporaryMemberId);
        RegionInfo preferredRegion = regionCache.getRegionInfoByCode(request.regionCode());
        Phone phone = validatePhoneDuplicate(request.phone());
        MarketingAgreement marketingAgreement = toMarketingAgreement(request);

        Member member = Member.toNormalMember(
                temporaryMember.getEmail(),
                temporaryMember.getPassword(),
                preferredRegion,
                request.name(),
                phone,
                marketingAgreement
        );

        Member savedMember = registerMember(temporaryMember, member);

        return generateTokenResponse(savedMember);
    }

    private static MarketingAgreement toMarketingAgreement(CompleteMemberProfileRequestDTO request) {
        return MarketingAgreement.of(
                request.agreements().marketing().sms(),
                request.agreements().marketing().push(),
                request.agreements().marketing().email()
        );
    }

    private Phone validatePhoneDuplicate(String phoneNumber) {
        Phone phone = Phone.of(phoneNumber);
        if (memberRepository.existsByPhone(phone)) {
            throw new DuplicateMemberException("이미 존재하는 전화번호입니다.");
        }
        return phone;
    }

    private TemporaryMember getTemporaryMember(Long temporaryMemberId) {
        TemporaryMember temporaryMember = temporaryMemberRepository.getById(temporaryMemberId);
        if (temporaryMember.isRegistered()) {
            throw new DuplicateMemberException();
        }
        return temporaryMember;
    }

    private CompleteMemberProfileResponseDTO generateTokenResponse(Member savedMember) {
        return CompleteMemberProfileResponseDTO.of(
                tokenProvider.createAccessToken(String.valueOf(savedMember.getId()), savedMember.getAuthority()),
                tokenProvider.createRefreshToken(String.valueOf(savedMember.getId()), savedMember.getAuthority()),
                savedMember.getId()
        );
    }

    private Member registerMember(TemporaryMember temporaryMember, Member member) {
        temporaryMember.markAsRegistered();
        return memberRepository.save(member);
    }
}
