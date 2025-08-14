package com.prography.lighton.member.users.application.query;

import com.prography.lighton.member.common.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.users.presentation.dto.response.CheckDuplicateEmailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CheckEmailExistService {

    private final MemberRepository memberRepository;

    public CheckDuplicateEmailResponse handle(String email) {
        return CheckDuplicateEmailResponse.of(memberRepository.existsByEmail(email));
    }
}
