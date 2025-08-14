package com.prography.lighton.member.users.application.command;

import com.prography.lighton.auth.application.port.AuthVerificationService;
import com.prography.lighton.auth.application.port.TokenProvider;
import com.prography.lighton.common.domain.vo.RegionInfo;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.domain.entity.TemporaryMember;
import com.prography.lighton.member.common.domain.entity.vo.MarketingAgreement;
import com.prography.lighton.member.common.domain.entity.vo.Phone;
import com.prography.lighton.member.common.domain.exception.DuplicateMemberException;
import com.prography.lighton.member.common.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.common.infrastructure.repository.TemporaryMemberRepository;
import com.prography.lighton.member.users.presentation.dto.request.CompleteMemberProfileRequest;
import com.prography.lighton.member.users.presentation.dto.response.CompleteMemberProfileResponse;
import com.prography.lighton.region.infrastructure.cache.RegionCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompleteProfileService {

    private final TemporaryMemberRepository temporaryMemberRepository;
    private final MemberRepository memberRepository;

    private final AuthVerificationService authVerificationService;
    private final TokenProvider tokenProvider;
    private final RegionCache regionCache;

    public CompleteMemberProfileResponse handle(final Long temporaryMemberId,
                                                final CompleteMemberProfileRequest request) {
        authVerificationService.checkIsVerified(request.phone());

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
                temporaryMember.getLoginType(),
                marketingAgreement
        );

        Member savedMember = registerMember(temporaryMember, member);

        return generateTokenResponse(savedMember);
    }

    private TemporaryMember getTemporaryMember(Long temporaryMemberId) {
        TemporaryMember temporaryMember = temporaryMemberRepository.getById(temporaryMemberId);
        if (temporaryMember.isRegistered()) {
            throw new DuplicateMemberException();
        }
        return temporaryMember;
    }

    private Phone validatePhoneDuplicate(String phoneNumber) {
        Phone phone = Phone.of(phoneNumber);
        if (memberRepository.existsByPhone(phone)) {
            throw new DuplicateMemberException("이미 존재하는 전화번호입니다.");
        }
        return phone;
    }

    private MarketingAgreement toMarketingAgreement(CompleteMemberProfileRequest request) {
        return MarketingAgreement.of(
                request.agreements().marketing().sms(),
                request.agreements().marketing().push(),
                request.agreements().marketing().email()
        );
    }

    private Member registerMember(TemporaryMember temporaryMember, Member member) {
        temporaryMember.markAsRegistered();
        return memberRepository.save(member);
    }

    private CompleteMemberProfileResponse generateTokenResponse(Member savedMember) {
        return CompleteMemberProfileResponse.of(
                tokenProvider.createAccessToken(String.valueOf(savedMember.getId()),
                        savedMember.getAuthority().toString()),
                tokenProvider.createRefreshToken(String.valueOf(savedMember.getId()),
                        savedMember.getAuthority().toString())
        );
    }

}
