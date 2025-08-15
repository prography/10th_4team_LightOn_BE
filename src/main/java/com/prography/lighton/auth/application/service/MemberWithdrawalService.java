package com.prography.lighton.auth.application.service;

import com.prography.lighton.artist.users.application.service.ArtistService;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.common.infrastructure.repository.TemporaryMemberRepository;
import com.prography.lighton.performance.users.application.service.UserPerformanceLikeService;
import com.prography.lighton.performance.users.application.service.UserPerformanceService;
import com.prography.lighton.performance.users.application.service.UserRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberWithdrawalService {

    private final MemberRepository memberRepository;
    private final TemporaryMemberRepository temporaryMemberRepository;
    private final UserPerformanceService userPerformanceService;
    private final UserPerformanceLikeService userPerformanceLikeService;
    private final UserRecommendationService userRecommendationService;
    private final ArtistService artistService;


    @Transactional
    public void withdraw(Member member) {
        Member dbMember = memberRepository.getMemberById(member.getId());
        dbMember.withdraw();

        userRecommendationService.deleteCache(member);
        userPerformanceService.inactivateAllByMember(dbMember);
        userPerformanceLikeService.inactivateAllByMember(dbMember);
        artistService.inactiveByMember(dbMember);

        temporaryMemberRepository.deleteByEmail(dbMember.getEmail());
    }
}
