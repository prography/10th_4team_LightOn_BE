package com.prography.lighton.member.application;

import static com.prography.lighton.member.domain.entity.vo.Email.*;
import static com.prography.lighton.member.domain.entity.vo.Password.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prography.lighton.member.domain.entity.TemporaryMember;
import com.prography.lighton.member.domain.repository.TemporaryMemberRepository;
import com.prography.lighton.member.exception.DuplicateMemberException;
import com.prography.lighton.member.presentation.dto.request.RegisterMemberRequestDTO;
import com.prography.lighton.member.presentation.dto.response.RegisterMemberResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
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
