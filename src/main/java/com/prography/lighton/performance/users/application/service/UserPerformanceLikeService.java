package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.PerformanceLike;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceLikeRepository;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRepository;
import com.prography.lighton.performance.users.presentation.dto.response.LikePerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserPerformanceLikeService {

    private final PerformanceRepository performanceRepository;
    private final PerformanceLikeRepository performanceLikeRepository;

    @Transactional
    public LikePerformanceResponse likeOrUnlikePerformance(Member member, Long performanceId) {
        Performance performance = performanceRepository.getById(performanceId);

        PerformanceLike like = performanceLikeRepository.findByMemberAndPerformance(member, performance)
                .orElseGet(() -> PerformanceLike.of(member, performance, false));

        boolean nowLiked = like.toggleLike();
        performanceLikeRepository.save(like);
        return LikePerformanceResponse.of(nowLiked);
    }
}

