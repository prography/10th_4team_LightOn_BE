package com.prography.lighton.member.presentation.dto.request;

public record CompleteMemberProfileRequestDTO(
		String name,
		String phone,
		RegionDTO region,
		AgreementsDTO agreements
) {
	public record RegionDTO(
			String city,
			String district
	) {}

	public record AgreementsDTO(
			boolean terms,
			boolean privacy,
			boolean over14,
			MarketingDTO marketing
	) {
		public record MarketingDTO(
				boolean sms,
				boolean push,
				boolean email
		) {}
	}
}