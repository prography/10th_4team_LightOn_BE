package com.prography.lighton.member.users.presentation.dto.response;

import com.prography.lighton.member.common.domain.entity.Member;

public record GetMemberInfoResponse(
        long id,
        String name,
        String email
) {
    public static GetMemberInfoResponse of(Member member) {
        return new GetMemberInfoResponse(member.getId(), member.getName(), member.getEmail().getValue());

    }
}
