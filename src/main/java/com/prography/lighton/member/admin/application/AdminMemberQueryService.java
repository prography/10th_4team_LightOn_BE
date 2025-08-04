package com.prography.lighton.member.admin.application;

import com.prography.lighton.member.admin.presentation.dto.response.GetMemberStatsResponse;
import com.prography.lighton.member.common.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminMemberQueryService {

    private final MemberRepository memberRepository;

    public GetMemberStatsResponse getMemberStats() {
        return GetMemberStatsResponse.of(memberRepository.count());
    }
}
