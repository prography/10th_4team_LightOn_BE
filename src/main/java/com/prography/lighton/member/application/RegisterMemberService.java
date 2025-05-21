package com.prography.lighton.member.application;

import static com.prography.lighton.member.domain.entity.vo.Email.of;
import static com.prography.lighton.member.domain.entity.vo.Password.encodeAndCreate;

import com.prography.lighton.auth.application.validator.DuplicateEmailValidator;
import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.member.domain.entity.TemporaryMember;
import com.prography.lighton.member.exception.DuplicateMemberException;
import com.prography.lighton.member.infrastructure.repository.TemporaryMemberRepository;
import com.prography.lighton.member.presentation.dto.request.RegisterMemberRequestDTO;
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
    private final PasswordEncoder passwordEncoder;
    private final DuplicateEmailValidator duplicateEmailValidator;

    @Override
    public RegisterMemberResponseDTO registerMember(final RegisterMemberRequestDTO request) {
        duplicateEmailValidator.validateConflictingLoginType(request.email(), SocialLoginType.DEFAULT);
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
