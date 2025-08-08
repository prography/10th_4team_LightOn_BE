package com.prography.lighton.common.aop;

import com.prography.lighton.auth.security.exception.ForbiddenException;
import com.prography.lighton.auth.security.exception.UnauthorizedException;
import com.prography.lighton.auth.security.util.SecurityUtils;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AdminCheckAspect {

    private final MemberRepository memberRepository;

    @Before("@annotation(com.prography.lighton.common.annotation.AdminOnly) || @within(com.prography.lighton.common.annotation.AdminOnly)")
    public void checkAdminAuthority() {
        Long memberId = SecurityUtils.getCurrentMemberId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UnauthorizedException("회원 정보가 존재하지 않습니다."));

        if (!member.isAdmin()) {
            throw new ForbiddenException("관리자 권한이 없습니다.");
        }
    }
}
