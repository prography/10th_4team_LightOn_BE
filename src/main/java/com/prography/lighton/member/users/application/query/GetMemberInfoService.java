package com.prography.lighton.member.users.application.query;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.users.presentation.dto.response.GetMemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetMemberInfoService {

    public GetMemberInfoResponse handle(Member member) {
        return GetMemberInfoResponse.of(member);
    }
}
