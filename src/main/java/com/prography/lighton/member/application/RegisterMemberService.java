package com.prography.lighton.member.application;

import static com.prography.lighton.member.domain.entity.vo.Password.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prography.lighton.member.application.command.RegisterMemberCommand;
import com.prography.lighton.member.domain.entity.TemporaryMember;
import com.prography.lighton.member.domain.repository.TemporaryMemberRepository;
import com.prography.lighton.member.domain.entity.vo.Email;
import com.prography.lighton.member.presentation.dto.response.SignUpMemberResponseDTO;

@Service
public class RegisterMemberService implements RegisterMemberUseCase {

	private final TemporaryMemberRepository temporaryMemberRepository;
	private final PasswordEncoder passwordEncoder;

	public RegisterMemberService(final TemporaryMemberRepository temporaryMemberRepository, final PasswordEncoder passwordEncoder) {
		this.temporaryMemberRepository = temporaryMemberRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public SignUpMemberResponseDTO registerMember(final RegisterMemberCommand command) {
		if (temporaryMemberRepository.existsByEmail(command.email())) {
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		}

		TemporaryMember temporaryMember = TemporaryMember.of(
				new Email(command.email()),
				encodeAndCreate(command.password(), passwordEncoder));
		temporaryMemberRepository.save(temporaryMember);

		return SignUpMemberResponseDTO.of(temporaryMember.getId());
	}
}
