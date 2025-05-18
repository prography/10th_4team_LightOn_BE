package com.prography.lighton.common.resolver;

import com.prography.lighton.auth.exception.InvalidTokenException;
import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class)
                && parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new InvalidTokenException("인증되지 않은 사용자입니다.");
        }

        String memberIdStr = authentication.getPrincipal().toString();
        Long memberId = Long.valueOf(memberIdStr);

        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidTokenException("존재하지 않는 사용자입니다."));
    }
}

