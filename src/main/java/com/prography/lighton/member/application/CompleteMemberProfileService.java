package com.prography.lighton.member.application;

import org.springframework.stereotype.Service;

import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.location.domain.entity.Region;
import com.prography.lighton.location.domain.entity.SubRegion;
import com.prography.lighton.location.domain.repository.RegionRepository;
import com.prography.lighton.location.domain.repository.SubRegionRepository;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.member.domain.entity.TemporaryMember;
import com.prography.lighton.member.domain.entity.vo.MarketingAgreement;
import com.prography.lighton.member.domain.entity.vo.Phone;
import com.prography.lighton.member.domain.entity.vo.PreferredRegion;
import com.prography.lighton.member.domain.repository.MemberRepository;
import com.prography.lighton.member.domain.repository.TemporaryMemberRepository;
import com.prography.lighton.member.presentation.dto.request.CompleteMemberProfileRequestDTO;
import com.prography.lighton.member.presentation.dto.response.CompleteMemberProfileResponseDTO;

@Service
public class CompleteMemberProfileService implements CompleteMemberProfileUseCase {

	private final TemporaryMemberRepository temporaryMemberRepository;
	private final MemberRepository memberRepository;
	private final RegionRepository regionRepository;
	private final SubRegionRepository subRegionRepository;

	private final TokenProvider tokenProvider;

	public CompleteMemberProfileService(
			final TemporaryMemberRepository temporaryMemberRepository,
			final MemberRepository memberRepository,
			final RegionRepository regionRepository,
			final SubRegionRepository subRegionRepository,
			final TokenProvider tokenProvider) {
		this.temporaryMemberRepository = temporaryMemberRepository;
		this.memberRepository = memberRepository;
		this.regionRepository = regionRepository;
		this.subRegionRepository = subRegionRepository;
		this.tokenProvider = tokenProvider;
	}

	@Override
	public CompleteMemberProfileResponseDTO completeMemberProfile(final Long temporaryMemberId, final CompleteMemberProfileRequestDTO request) {
		TemporaryMember temporaryMember = temporaryMemberRepository.getById(temporaryMemberId);
		if (temporaryMember.isRegistered()) {
			throw new IllegalArgumentException("이미 프로필이 등록된 회원입니다.");
		}

		Region region = regionRepository.getByRegionCode(request.regionCode());
		SubRegion subRegion = subRegionRepository.getByRegionCode(request.regionCode());

		Member member = Member.toNormalMember(
				temporaryMember.getEmail(),
				temporaryMember.getPassword(),
				PreferredRegion.of(region, subRegion),
				request.name(),
				Phone.of(request.phone()),
				MarketingAgreement.of(
						request.agreements().marketing().sms(),
						request.agreements().marketing().push(),
						request.agreements().marketing().email())
		);

		temporaryMember.markAsRegistered();
		Member savedMember = memberRepository.save(member);

		return CompleteMemberProfileResponseDTO.of(
				tokenProvider.createAccessToken(String.valueOf(savedMember.getId())),
				tokenProvider.createRefreshToken(String.valueOf(savedMember.getId())),
				savedMember.getId()
		);
	}
}
