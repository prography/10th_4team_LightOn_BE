package com.prography.lighton.member.users.application;

import static com.prography.lighton.member.common.domain.entity.vo.Email.of;
import static com.prography.lighton.member.common.domain.entity.vo.Password.encodeAndCreate;

import com.prography.lighton.auth.application.port.AuthVerificationService;
import com.prography.lighton.auth.application.port.TokenProvider;
import com.prography.lighton.common.domain.vo.RegionInfo;
import com.prography.lighton.genre.infrastructure.cache.GenreCache;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.domain.entity.TemporaryMember;
import com.prography.lighton.member.common.domain.entity.association.PreferredGenre;
import com.prography.lighton.member.common.domain.entity.vo.MarketingAgreement;
import com.prography.lighton.member.common.domain.entity.vo.Phone;
import com.prography.lighton.member.common.domain.exception.DuplicateMemberException;
import com.prography.lighton.member.common.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.common.infrastructure.repository.PreferredGenreRepository;
import com.prography.lighton.member.common.infrastructure.repository.TemporaryMemberRepository;
import com.prography.lighton.member.users.presentation.dto.request.CompleteMemberProfileRequest;
import com.prography.lighton.member.users.presentation.dto.request.EditMemberGenreRequest;
import com.prography.lighton.member.users.presentation.dto.request.RegisterMemberRequest;
import com.prography.lighton.member.users.presentation.dto.response.CompleteMemberProfileResponse;
import com.prography.lighton.member.users.presentation.dto.response.RegisterMemberResponse;
import com.prography.lighton.region.infrastructure.cache.RegionCache;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserMemberCommandService {

    private final TemporaryMemberRepository temporaryMemberRepository;
    private final MemberRepository memberRepository;
    private final PreferredGenreRepository preferredGenreRepository;

    private final PasswordEncoder passwordEncoder;
    private final GenreCache genreCache;
    private final RegionCache regionCache;
    private final TokenProvider tokenProvider;
    private final AuthVerificationService authVerificationService;

    public CompleteMemberProfileResponse completeMemberProfile(final Long temporaryMemberId,
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

    public RegisterMemberResponse registerMember(final RegisterMemberRequest request) {
        TemporaryMember temporaryMember = TemporaryMember.of(
                of(request.email()),
                encodeAndCreate(request.password(), passwordEncoder));
        temporaryMemberRepository.save(temporaryMember);

        return RegisterMemberResponse.of(temporaryMember.getId());
    }

    public void editMemberGenre(final Member member, EditMemberGenreRequest request) {
        deletePreviousPreferredGenres(member);

        List<PreferredGenre> preferredGenres = genreCache.getGenresByNameOrThrow(request.genres()).stream()
                .map((genre -> PreferredGenre.of(member, genre)))
                .toList();
        member.editPreferredGenres(preferredGenres);

        preferredGenreRepository.saveAll(preferredGenres);
    }

    public void inactivateAllByMember(Member member) {
        deletePreviousPreferredGenres(member);
    }

    private static MarketingAgreement toMarketingAgreement(CompleteMemberProfileRequest request) {
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

    private CompleteMemberProfileResponse generateTokenResponse(Member savedMember) {
        return CompleteMemberProfileResponse.of(
                tokenProvider.createAccessToken(String.valueOf(savedMember.getId()),
                        savedMember.getAuthority().toString()),
                tokenProvider.createRefreshToken(String.valueOf(savedMember.getId()),
                        savedMember.getAuthority().toString())
        );
    }

    private Member registerMember(TemporaryMember temporaryMember, Member member) {
        temporaryMember.markAsRegistered();
        return memberRepository.save(member);
    }

    private void deletePreviousPreferredGenres(Member member) {
        preferredGenreRepository.deleteAllByMember(member);
    }
}
