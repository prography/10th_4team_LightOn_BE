package com.prography.lighton.auth.application.impl;

import com.prography.lighton.auth.application.OAuthUseCase;
import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.auth.exception.MemberProfileIncompleteException;
import com.prography.lighton.auth.presentation.dto.google.GoogleOAuthToken;
import com.prography.lighton.auth.presentation.dto.google.GoogleUser;
import com.prography.lighton.auth.presentation.dto.kakao.KaKaoOAuthTokenDTO;
import com.prography.lighton.auth.presentation.dto.kakao.KaKaoUser;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.member.domain.entity.TemporaryMember;
import com.prography.lighton.member.domain.entity.vo.Email;
import com.prography.lighton.member.domain.entity.vo.Password;
import com.prography.lighton.member.domain.repository.MemberRepository;
import com.prography.lighton.member.domain.repository.TemporaryMemberRepository;
import com.prography.lighton.member.presentation.dto.response.LoginMemberResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService implements OAuthUseCase {

    private final KaKaoOauth kaKaoOauth;
    private final GoogleOauth googleOauth;
    private final TokenProvider tokenProvider;

    private final TemporaryMemberRepository temporaryMemberRepository;
    private final MemberRepository memberRepository;

    @Override
    public String accessRequest(SocialLoginType socialLoginType) {
        return switch (socialLoginType) {
            case GOOGLE -> googleOauth.getOauthRedirectURL();
            case KAKAO -> kaKaoOauth.getOauthRedirectURL();
            case APPLE -> throw new UnsupportedOperationException("Apple login not implemented");
        };
    }

    @Override
    public LoginMemberResponseDTO oAuthLoginOrJoin(SocialLoginType socialLoginType, String code) {
        String email = extractEmailFromSocialProvider(socialLoginType, code);
        return handleLoginOrRegister(email);
    }

    private String extractEmailFromSocialProvider(SocialLoginType socialLoginType, String code) {
        return switch (socialLoginType) {
            case KAKAO -> {
                KaKaoOAuthTokenDTO token = kaKaoOauth.requestAccessToken(code);
                KaKaoUser user = kaKaoOauth.requestUserInfo(token);
                yield user.kakao_account().email() == null ? "kminseok5167@gmail.com" : user.kakao_account().email();
            }
            case GOOGLE -> {
                GoogleOAuthToken token = googleOauth.requestAccessToken(code);
                GoogleUser user = googleOauth.requestUserInfo(token);
                yield user.email();
            }
            case APPLE -> throw new UnsupportedOperationException("Apple login not implemented");
        };
    }

    private LoginMemberResponseDTO handleLoginOrRegister(String email) {
        Email emailVO = Email.of(email);

        if (isExistTemporaryMemberByEmail(email)) {
            throw new MemberProfileIncompleteException();
        }

        if (isExistMemberByEmail(email)) {
            Member member = memberRepository.findByEmail(emailVO).orElseThrow();
            String accessToken = tokenProvider.createAccessToken(member.getId().toString());
            String refreshToken = tokenProvider.createRefreshToken(member.getId().toString());
            return LoginMemberResponseDTO.registered(accessToken, refreshToken, member.getId());
        }

        TemporaryMember tempMember = temporaryMemberRepository.findByEmail(emailVO)
                .orElseGet(() -> temporaryMemberRepository.save(
                        TemporaryMember.of(emailVO, Password.forSocialLogin())
                ));

        return LoginMemberResponseDTO.temporary(tempMember.getId());
    }

    private boolean isExistTemporaryMemberByEmail(String email) {
        return temporaryMemberRepository.existsByEmailAndNotRegistered(email);
    }

    private boolean isExistMemberByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
}
