package com.prography.lighton.auth.application.impl;

import com.prography.lighton.artist.users.application.service.ArtistService;
import com.prography.lighton.auth.application.AuthService;
import com.prography.lighton.auth.application.AuthVerificationService;
import com.prography.lighton.auth.application.RefreshTokenService;
import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.auth.application.exception.PhoneVerificationFailedException;
import com.prography.lighton.auth.domain.vo.VerificationCode;
import com.prography.lighton.auth.infrastructure.sms.SmsService;
import com.prography.lighton.auth.presentation.dto.request.VerifyPhoneRequestDTO;
import com.prography.lighton.auth.presentation.dto.response.ReissueTokenResponse;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.users.application.ManagePreferredGenreUseCase;
import com.prography.lighton.member.users.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.users.infrastructure.repository.TemporaryMemberRepository;
import com.prography.lighton.performance.users.application.service.UserPerformanceLikeService;
import com.prography.lighton.performance.users.application.service.UserPerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final static String ROLE_KEY = "roles";

    private final TemporaryMemberRepository temporaryMemberRepository;
    private final MemberRepository memberRepository;

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final SmsService smsService;
    private final AuthVerificationService authVerificationService;

    private final UserPerformanceService userPerformanceService;
    private final UserPerformanceLikeService userPerformanceLikeService;
    private final ManagePreferredGenreUseCase managePreferredGenreUseCase;
    private final ArtistService artistService;

    @Override
    public ReissueTokenResponse reissueLoginTokens(String refreshToken) {
        var claims = tokenProvider.getClaims(refreshToken);

        var userId = claims.getSubject();
        var role = claims.get(ROLE_KEY, String.class);

        refreshTokenService.validateRefreshToken(userId, refreshToken);

        String newAccessToken = tokenProvider.createAccessToken(userId, role);
        String newRefreshToken = tokenProvider.createRefreshToken(userId, role);
        refreshTokenService.saveRefreshToken(userId, newRefreshToken);

        return ReissueTokenResponse.of(newAccessToken, newRefreshToken);
    }

    @Override
    public void logout(Member member) {
        refreshTokenService.deleteRefreshToken(member.getId().toString());
    }

    @Override
    @Transactional
    public void withdraw(Member member) {
        Member dbMember = memberRepository.getMemberById(member.getId());
        member.withdraw();
        memberRepository.flush();

        userPerformanceService.inactivateAllByMember(dbMember);
        managePreferredGenreUseCase.inactivateAllByMember(dbMember);
        userPerformanceLikeService.inactivateAllByMember(dbMember);
        artistService.inactiveByMember(dbMember);

        temporaryMemberRepository.deleteByEmail(dbMember.getEmail());
    }

    @Override
    public void sendAuthCode(String phoneNumber) {
        VerificationCode code = VerificationCode.generateCode();
        smsService.sendSms(phoneNumber, code.getValue());
        authVerificationService.saveCode(phoneNumber, code.getValue());
    }

    @Override
    public void verifyPhone(VerifyPhoneRequestDTO request) {
        if (!authVerificationService.isCodeMatched(request.phoneNumber(), request.code())) {
            throw new PhoneVerificationFailedException();
        }
        authVerificationService.saveVerifiedStatus(request.phoneNumber());
    }
}
