package com.prography.lighton.member.users.application;

import static com.prography.lighton.member.users.domain.entity.vo.Email.of;
import static com.prography.lighton.member.users.domain.entity.vo.Password.encodeAndCreate;

import com.prography.lighton.member.users.domain.entity.TemporaryMember;
import com.prography.lighton.member.users.domain.exception.DuplicateMemberException;
import com.prography.lighton.member.users.infrastructure.repository.TemporaryMemberRepository;
import com.prography.lighton.member.users.presentation.dto.request.RegisterMemberRequestDTO;
import com.prography.lighton.member.users.presentation.dto.response.RegisterMemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterMemberService implements RegisterMemberUseCase {

    private final TemporaryMemberRepository temporaryMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterMemberResponseDTO registerMember(final RegisterMemberRequestDTO request) {
        if (temporaryMemberRepository.existsByEmail(request.email())) {
            throw new DuplicateMemberException("이미 존재하는 이메일입니다.");
        }

        TemporaryMember temporaryMember = TemporaryMember.of(
                of(request.email()),
                encodeAndCreate(request.password(), passwordEncoder));
        temporaryMemberRepository.save(temporaryMember);

        return RegisterMemberResponseDTO.of(temporaryMember.getId());
    }
}
