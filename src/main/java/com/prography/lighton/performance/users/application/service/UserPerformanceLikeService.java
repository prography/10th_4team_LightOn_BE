package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.artist.users.application.service.ArtistLikeRedisService;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.PerformanceLike;
import com.prography.lighton.performance.common.domain.entity.enums.Type;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceLikeRepository;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRepository;
import com.prography.lighton.performance.users.presentation.dto.response.LikePerformanceResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserPerformanceLikeService {

    private final PerformanceRepository performanceRepository;
    private final PerformanceLikeRepository performanceLikeRepository;
    private final ArtistLikeRedisService likeRedisService;

    @Transactional
    public LikePerformanceResponse likeOrUnlikePerformance(Member member, Long performanceId) {
        Performance performance = performanceRepository.getById(performanceId);

        PerformanceLike like = performanceLikeRepository.findByMemberAndPerformance(member, performance)
                .orElseGet(() -> PerformanceLike.of(member, performance, false));

        boolean nowLiked = like.toggleLike();
        performanceLikeRepository.save(like);

        if (like.toggleLike()) {
            incrementArtistLikes(performance);
        }
        return LikePerformanceResponse.of(nowLiked);
    }

    private void incrementArtistLikes(Performance performance) {
        if (performance.getType() != Type.CONCERT) {
            return;
        }
        List<Long> artistIds = performance.getArtists().stream()
                .map(pa -> pa.getArtist().getId())
                .toList();

        if (!artistIds.isEmpty()) {
            likeRedisService.incrementToday(artistIds);
        }
    }

    public void inactivateAllByMember(Member member) {
        performanceLikeRepository.deleteAllByMember(member);
    }
}

