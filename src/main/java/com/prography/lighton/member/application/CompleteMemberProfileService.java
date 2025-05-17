package com.prography.lighton.member.application;

import org.springframework.stereotype.Service;

import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.member.domain.entity.TemporaryMember;
import com.prography.lighton.member.domain.entity.vo.MarketingAgreement;
import com.prography.lighton.member.domain.entity.vo.Phone;
import com.prography.lighton.member.domain.entity.vo.PreferredRegion;
import com.prography.lighton.member.domain.repository.MemberRepository;
import com.prography.lighton.member.domain.repository.TemporaryMemberRepository;
import com.prography.lighton.member.presentation.dto.request.CompleteMemberProfileRequestDTO;

@Service
public class CompleteMemberProfileService implements CompleteMemberProfileUseCase {

	private final TemporaryMemberRepository temporaryMemberRepository;
	private final MemberRepository memberRepository;
	private final TokenProvider tokenProvider;

	public CompleteMemberProfileService(
			final TemporaryMemberRepository temporaryMemberRepository,
			final MemberRepository memberRepository,
			final TokenProvider tokenProvider) {
		this.temporaryMemberRepository = temporaryMemberRepository;
		this.memberRepository = memberRepository;
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void completeMemberProfile(final Long temporaryMemberId, final CompleteMemberProfileRequestDTO request) {
		TemporaryMember temporaryMember = temporaryMemberRepository.getById(temporaryMemberId);
		if (temporaryMember.isRegistered()) {
			throw new IllegalArgumentException("이미 프로필이 등록된 회원입니다.");
		}

		Member member = Member.toNormalMember(
				temporaryMember.getEmail(),
				temporaryMember.getPassword(),
				new PreferredRegion(),
				request.name(),
				Phone.of(request.phone()),
				MarketingAgreement.of(
						request.agreements().marketing().sms(),
						request.agreements().marketing().push(),
						request.agreements().marketing().email())
		);

		memberRepository.save(member);
		temporaryMember.markAsRegistered();
	}
}
