package com.prography.lighton.member.users.application;

import static com.prography.lighton.member.common.domain.entity.vo.Email.of;
import static com.prography.lighton.member.common.domain.entity.vo.Password.encodeAndCreate;

import com.prography.lighton.member.common.domain.entity.TemporaryMember;
import com.prography.lighton.member.users.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.users.infrastructure.repository.TemporaryMemberRepository;
import com.prography.lighton.member.users.presentation.dto.request.RegisterMemberRequestDTO;
import com.prography.lighton.member.users.presentation.dto.response.CheckDuplicateEmailResponseDTO;
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
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterMemberResponseDTO registerMember(final RegisterMemberRequestDTO request) {
        TemporaryMember temporaryMember = TemporaryMember.of(
                of(request.email()),
                encodeAndCreate(request.password(), passwordEncoder));
        temporaryMemberRepository.save(temporaryMember);

        return RegisterMemberResponseDTO.of(temporaryMember.getId());
    }

    @Override
    public CheckDuplicateEmailResponseDTO checkEmailExists(String email) {
        return CheckDuplicateEmailResponseDTO.of(memberRepository.existsByEmail(email));
    }
}
