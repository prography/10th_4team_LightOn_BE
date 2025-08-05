package com.prography.lighton.auth.application.validator;

import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.member.common.domain.exception.DuplicateMemberException;
import com.prography.lighton.member.common.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.common.infrastructure.repository.TemporaryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DuplicateEmailValidator {

    private final MemberRepository memberRepository;
    private final TemporaryMemberRepository temporaryMemberRepository;

    public void validateConflictingLoginType(String email, SocialLoginType loginType) {
        if (memberRepository.existsConflictingLoginTypeByEmail(email, loginType) ||
                temporaryMemberRepository.existsConflictingLoginTypeByEmail(email, loginType)) {
            throw new DuplicateMemberException("이미 해당 이메일로 다른 로그인 방식으로 가입된 계정이 존재합니다.");
        }
    }
}
