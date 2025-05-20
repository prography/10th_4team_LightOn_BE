package com.prography.lighton.auth.application.impl;

import com.prography.lighton.auth.application.OAuthUseCase;
import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.auth.application.exception.MemberProfileIncompleteException;
import com.prography.lighton.auth.application.exception.UnsupportedSocialLoginTypeException;
import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.auth.presentation.dto.google.GoogleOAuthToken;
import com.prography.lighton.auth.presentation.dto.google.GoogleUser;
import com.prography.lighton.auth.presentation.dto.kakao.KaKaoOAuthTokenDTO;
import com.prography.lighton.auth.presentation.dto.kakao.KaKaoUser;
import com.prography.lighton.auth.presentation.dto.response.login.LoginSocialMemberResponseDTO;
import com.prography.lighton.auth.presentation.dto.response.login.RegisterSocialMemberResponseDTO;
import com.prography.lighton.auth.presentation.dto.response.login.SocialLoginResult;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.member.domain.entity.TemporaryMember;
import com.prography.lighton.member.domain.entity.vo.Email;
import com.prography.lighton.member.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.infrastructure.repository.TemporaryMemberRepository;
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
            case APPLE -> throw new UnsupportedOperationException("Apple 로그인은 아직 지원되지 않습니다.");
            default -> throw new UnsupportedSocialLoginTypeException("지원하지 않는 로그인 타입입니다.");
        };
    }

    @Override
    public SocialLoginResult oAuthLoginOrJoin(SocialLoginType socialLoginType, String code) {
        String email = extractEmailFromSocialProvider(socialLoginType, code);
        return handleLoginOrRegister(email);
    }

    private String extractEmailFromSocialProvider(SocialLoginType socialLoginType, String code) {
        return switch (socialLoginType) {
            case KAKAO -> {
                KaKaoOAuthTokenDTO token = kaKaoOauth.requestAccessToken(code);
                KaKaoUser user = kaKaoOauth.requestUserInfo(token);
                yield user.kakao_account().email();
            }
            case GOOGLE -> {
                GoogleOAuthToken token = googleOauth.requestAccessToken(code);
                GoogleUser user = googleOauth.requestUserInfo(token);
                yield user.email();
            }
            case APPLE -> throw new UnsupportedOperationException("Apple 로그인은 아직 지원되지 않습니다.");
            default -> throw new UnsupportedSocialLoginTypeException("지원하지 않는 로그인 타입입니다.");
        };
    }

    private SocialLoginResult handleLoginOrRegister(String email) {
        Email emailVO = Email.of(email);

        if (isExistTemporaryMemberByEmail(email)) {
            throw new MemberProfileIncompleteException();
        }

        if (isExistMemberByEmail(email)) {
            Member member = memberRepository.getMemberByEmail(emailVO);
            return issueTokensFor(member);
        }

        TemporaryMember tempMember = temporaryMemberRepository.findByEmail(emailVO)
                .orElseGet(() -> temporaryMemberRepository.save(TemporaryMember.socialLoginMemberOf(emailVO)));

        return RegisterSocialMemberResponseDTO.of(tempMember.isRegistered(), tempMember.getId());
    }

    private boolean isExistTemporaryMemberByEmail(String email) {
        return temporaryMemberRepository.existsByEmailAndNotRegistered(email);
    }

    private boolean isExistMemberByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    private LoginSocialMemberResponseDTO issueTokensFor(Member member) {
        return LoginSocialMemberResponseDTO.from(
                true,
                tokenProvider.createAccessToken(String.valueOf(member.getId()), member.getAuthority()),
                tokenProvider.createRefreshToken(String.valueOf(member.getId()), member.getAuthority())
        );
    }
}
