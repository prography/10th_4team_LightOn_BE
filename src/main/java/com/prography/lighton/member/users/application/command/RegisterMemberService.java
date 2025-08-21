package com.prography.lighton.member.users.application.command;

import com.prography.lighton.member.common.domain.entity.TemporaryMember;
import com.prography.lighton.member.common.domain.entity.vo.Email;
import com.prography.lighton.member.common.domain.entity.vo.Password;
import com.prography.lighton.member.common.infrastructure.repository.TemporaryMemberRepository;
import com.prography.lighton.member.users.presentation.dto.request.RegisterMemberRequest;
import com.prography.lighton.member.users.presentation.dto.response.RegisterMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterMemberService {
    private final TemporaryMemberRepository temporaryMemberRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterMemberResponse handle(RegisterMemberRequest request) {
        TemporaryMember tmp = TemporaryMember.of(
                Email.of(request.email()),
                Password.encodeAndCreate(request.password(), passwordEncoder)
        );
        temporaryMemberRepository.save(tmp);
        return RegisterMemberResponse.of(tmp.getId());
    }
}
