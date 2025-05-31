package com.prography.lighton.member.application;

import static com.prography.lighton.member.domain.entity.vo.Email.of;
import static com.prography.lighton.member.domain.entity.vo.Password.encodeAndCreate;

import com.prography.lighton.member.domain.entity.TemporaryMember;
import com.prography.lighton.member.domain.exception.DuplicateMemberException;
import com.prography.lighton.member.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.infrastructure.repository.TemporaryMemberRepository;
import com.prography.lighton.member.presentation.dto.request.RegisterMemberRequestDTO;
import com.prography.lighton.member.presentation.dto.response.CheckDuplicateEmailResponseDTO;
import com.prography.lighton.member.presentation.dto.response.RegisterMemberResponseDTO;
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
        if (temporaryMemberRepository.existsByEmail(request.email())) {
            throw new DuplicateMemberException("이미 존재하는 이메일입니다.");
        }

        TemporaryMember temporaryMember = TemporaryMember.of(
                of(request.email()),
                encodeAndCreate(request.password(), passwordEncoder));
        temporaryMemberRepository.save(temporaryMember);

        return RegisterMemberResponseDTO.of(temporaryMember.getId());
    }

    @Override
    public CheckDuplicateEmailResponseDTO checkEmailExists(String email) {
        boolean isDuplicate = temporaryMemberRepository.existsByEmail(email)
                || memberRepository.existsByEmail(email);

        return CheckDuplicateEmailResponseDTO.of(isDuplicate);
    }
}
