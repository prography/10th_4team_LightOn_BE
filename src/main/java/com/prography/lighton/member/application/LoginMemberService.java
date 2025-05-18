package com.prography.lighton.member.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.member.domain.entity.vo.Email;
import com.prography.lighton.member.domain.entity.vo.Password;
import com.prography.lighton.member.domain.repository.MemberRepository;
import com.prography.lighton.member.domain.repository.TemporaryMemberRepository;
import com.prography.lighton.member.exception.InvalidMemberException;
import com.prography.lighton.member.presentation.dto.request.LoginMemberRequestDTO;
import com.prography.lighton.member.presentation.dto.response.LoginMemberResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginMemberService implements LoginMemberUseCase {

	private final MemberRepository memberRepository;
	private final TemporaryMemberRepository temporaryMemberRepository;

	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;

	@Override
	public LoginMemberResponseDTO loginMember(LoginMemberRequestDTO request) {
		checkMemberCompleteMemberProfile(request);

		Member loginMember = memberRepository.getMemberByEmail(Email.of(request.email()));

		loginMember.validatePassword(request.password(), passwordEncoder);

		return generateTokenResponse(loginMember);
	}

	private void checkMemberCompleteMemberProfile(LoginMemberRequestDTO request) {
		if (temporaryMemberRepository.existsByEmail(request.email())) {
			throw new InvalidMemberException("개인 정보를 입력해주세요. 개인 정보 입력 후 로그인 가능합니다.");
		}
	}

	private LoginMemberResponseDTO generateTokenResponse(Member member) {
		return LoginMemberResponseDTO.of(
				tokenProvider.createAccessToken(String.valueOf(member.getId())),
				tokenProvider.createRefreshToken(String.valueOf(member.getId())),
				member.getId()
		);
	}
}
