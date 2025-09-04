package com.prography.lighton.auth.application.service;

import com.prography.lighton.auth.application.port.TokenProvider;
import com.prography.lighton.auth.application.validator.DuplicateEmailValidator;
import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.domain.entity.vo.Email;
import com.prography.lighton.member.common.domain.exception.InvalidMemberException;
import com.prography.lighton.member.common.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.common.infrastructure.repository.TemporaryMemberRepository;
import com.prography.lighton.member.users.presentation.dto.request.LoginMemberRequest;
import com.prography.lighton.member.users.presentation.dto.response.LoginMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginMemberService {

    private final MemberRepository memberRepository;
    private final TemporaryMemberRepository temporaryMemberRepository;

    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final DuplicateEmailValidator duplicateEmailValidator;

    public LoginMemberResponse login(LoginMemberRequest request) {
        duplicateEmailValidator.validateConflictingLoginType(request.email(), SocialLoginType.DEFAULT);
        validateIsNotTemporaryMember(request.email());

        Member loginMember = memberRepository.getMemberByEmail(Email.of(request.email()));
        loginMember.validatePassword(request.password(), passwordEncoder);

        return issueTokensFor(loginMember);
    }

    private void validateIsNotTemporaryMember(String email) {
        if (temporaryMemberRepository.existsByEmailAndSocialLoginTypeAndNotRegistered(email, SocialLoginType.DEFAULT)) {
            throw new InvalidMemberException("개인 정보를 입력해주세요.");
        }
    }

    private LoginMemberResponse issueTokensFor(Member member) {
        return LoginMemberResponse.of(
                tokenProvider.createAccessToken(String.valueOf(member.getId()), member.getAuthority().toString()),
                tokenProvider.createRefreshToken(String.valueOf(member.getId()), member.getAuthority().toString())
        );
    }


}
