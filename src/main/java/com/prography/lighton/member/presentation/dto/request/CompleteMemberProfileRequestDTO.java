package com.prography.lighton.member.presentation.dto.request;

public record CompleteMemberProfileRequestDTO(
		String name,
		String phone,
		Integer regionCode,
		AgreementsDTO agreements
) {
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
