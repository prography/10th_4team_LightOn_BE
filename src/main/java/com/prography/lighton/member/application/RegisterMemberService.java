package com.prography.lighton.member.application;

import org.springframework.stereotype.Service;

import com.prography.lighton.member.application.command.RegisterMemberCommand;
import com.prography.lighton.member.domain.MemberRepository;
import com.prography.lighton.member.presentation.dto.response.SignUpMemberResponseDTO;

@Service
public class RegisterMemberService implements RegisterMemberUseCase {

	private final MemberRepository memberRepository;

	public RegisterMemberService(final MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public SignUpMemberResponseDTO registerMember(RegisterMemberCommand command) throws Exception {
		return null;
	}
}
