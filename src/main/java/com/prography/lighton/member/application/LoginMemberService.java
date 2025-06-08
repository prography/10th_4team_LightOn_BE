package com.prography.lighton.member.application;

import com.prography.lighton.auth.application.RefreshTokenService;
import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.auth.application.validator.DuplicateEmailValidator;
import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.member.domain.entity.vo.Email;
import com.prography.lighton.member.domain.exception.InvalidMemberException;
import com.prography.lighton.member.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.infrastructure.repository.TemporaryMemberRepository;
import com.prography.lighton.member.presentation.dto.request.LoginMemberRequestDTO;
import com.prography.lighton.member.presentation.dto.response.LoginMemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginMemberService implements LoginMemberUseCase {

    private final MemberRepository memberRepository;
    private final TemporaryMemberRepository temporaryMemberRepository;

    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final DuplicateEmailValidator duplicateEmailValidator;
    private final RefreshTokenService refreshTokenService;

    @Override
    public LoginMemberResponseDTO login(LoginMemberRequestDTO request) {
        duplicateEmailValidator.validateConflictingLoginType(request.email(), SocialLoginType.DEFAULT);
        validateIsNotTemporaryMember(request.email());

        Member loginMember = memberRepository.getMemberByEmail(Email.of(request.email()));
        loginMember.validatePassword(request.password(), passwordEncoder);

        return issueTokensFor(loginMember);
    }

    private void validateIsNotTemporaryMember(String email) {
        if (temporaryMemberRepository.existsByEmailAndNotRegistered(email)) {
            throw new InvalidMemberException("개인 정보를 입력해주세요.");
        }
    }

    private LoginMemberResponseDTO issueTokensFor(Member member) {
        return LoginMemberResponseDTO.of(
                tokenProvider.createAccessToken(String.valueOf(member.getId()), member.getAuthority().toString()),
                createAndSaveRefreshToken(member)
        );
    }

    private String createAndSaveRefreshToken(Member member) {
        String refreshToken = tokenProvider.createRefreshToken(String.valueOf(member.getId()),
                member.getAuthority().toString());

        refreshTokenService.save(String.valueOf(member.getId()), refreshToken);
        return refreshToken;
    }
}
