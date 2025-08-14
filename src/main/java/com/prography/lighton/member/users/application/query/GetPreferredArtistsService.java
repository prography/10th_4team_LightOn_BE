package com.prography.lighton.member.users.application.query;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.users.presentation.dto.response.GetMyPreferredArtistsResponse;
import com.prography.lighton.performance.users.application.service.ArtistPerformanceService;
import com.prography.lighton.performance.users.application.service.UserPerformanceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetPreferredArtistsService {

    private final UserPerformanceService userPerformanceService;
    private final ArtistPerformanceService artistPerformanceService;

    public GetMyPreferredArtistsResponse handle(Member member) {
        // 현재 구현은 내가 신청했던 공연의 아티스트 조회 - 추후 아티스트 페이지 개발 시 변경 예정
        List<Long> appliedPerformanceIds = userPerformanceService.getAppliedPerformances(member);
        if (appliedPerformanceIds.isEmpty()) {
            return GetMyPreferredArtistsResponse.of(List.of());
        }

        List<Artist> preferredArtists = artistPerformanceService.getArtistsByAppliedPerformanceIds(
                appliedPerformanceIds);
        return GetMyPreferredArtistsResponse.of(preferredArtists);
    }
}
