package com.prography.lighton.member.application;

import org.springframework.stereotype.Service;

import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.member.exception.DuplicateMemberException;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.member.domain.entity.TemporaryMember;
import com.prography.lighton.member.domain.entity.vo.MarketingAgreement;
import com.prography.lighton.member.domain.entity.vo.Phone;
import com.prography.lighton.member.domain.entity.vo.PreferredRegion;
import com.prography.lighton.member.domain.repository.MemberRepository;
import com.prography.lighton.member.domain.repository.TemporaryMemberRepository;
import com.prography.lighton.member.presentation.dto.request.CompleteMemberProfileRequestDTO;
import com.prography.lighton.member.presentation.dto.response.CompleteMemberProfileResponseDTO;
import com.prography.lighton.region.domain.resolver.PreferredRegionResolver;

@Service
public class CompleteMemberProfileService implements CompleteMemberProfileUseCase {

	private final TemporaryMemberRepository temporaryMemberRepository;
	private final MemberRepository memberRepository;

	private final PreferredRegionResolver preferredRegionResolver;
	private final TokenProvider tokenProvider;

	public CompleteMemberProfileService(
			final TemporaryMemberRepository temporaryMemberRepository,
			final MemberRepository memberRepository,
			final PreferredRegionResolver preferredRegionResolver,
			final TokenProvider tokenProvider) {
		this.temporaryMemberRepository = temporaryMemberRepository;
		this.memberRepository = memberRepository;
		this.preferredRegionResolver = preferredRegionResolver;
		this.tokenProvider = tokenProvider;
	}

	@Override
	public CompleteMemberProfileResponseDTO completeMemberProfile(final Long temporaryMemberId, final CompleteMemberProfileRequestDTO request) {
		TemporaryMember temporaryMember = getTemporaryMember(temporaryMemberId);
		PreferredRegion preferredRegion = preferredRegionResolver.resolve(request.regionCode());
		Phone phone = validatePhoneDuplicate(request.phone());
		MarketingAgreement marketingAgreement = toMarketingAgreement(request);

		Member member = Member.toNormalMember(
				temporaryMember.getEmail(),
				temporaryMember.getPassword(),
				preferredRegion,
				request.name(),
				phone,
				marketingAgreement
		);

		Member savedMember = registerMember(temporaryMember, member);

		return generateTokenResponse(savedMember);
	}

	private static MarketingAgreement toMarketingAgreement(CompleteMemberProfileRequestDTO request) {
		return MarketingAgreement.of(
				request.agreements().marketing().sms(),
				request.agreements().marketing().push(),
				request.agreements().marketing().email()
		);
	}

	private Phone validatePhoneDuplicate(String phoneNumber) {
		Phone phone = Phone.of(phoneNumber);
		if (memberRepository.existsByPhone(phone)) {
			throw new DuplicateMemberException("이미 존재하는 전화번호입니다.");
		}
		return phone;
	}

	private TemporaryMember getTemporaryMember(Long temporaryMemberId) {
		TemporaryMember temporaryMember = temporaryMemberRepository.getById(temporaryMemberId);
		if (temporaryMember.isRegistered()) {
			throw new DuplicateMemberException();
		}
		return temporaryMember;
	}

	private CompleteMemberProfileResponseDTO generateTokenResponse(Member savedMember) {
		return CompleteMemberProfileResponseDTO.of(
				tokenProvider.createAccessToken(String.valueOf(savedMember.getId())),
				tokenProvider.createRefreshToken(String.valueOf(savedMember.getId())),
				savedMember.getId()
		);
	}

	private Member registerMember(TemporaryMember temporaryMember, Member member) {
		temporaryMember.markAsRegistered();
		return memberRepository.save(member);
	}
}
