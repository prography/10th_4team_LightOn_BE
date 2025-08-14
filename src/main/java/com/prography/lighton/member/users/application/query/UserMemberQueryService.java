package com.prography.lighton.member.users.application.query;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.domain.entity.association.PreferredGenre;
import com.prography.lighton.member.common.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.common.infrastructure.repository.PreferredGenreRepository;
import com.prography.lighton.member.users.presentation.dto.response.CheckDuplicateEmailResponse;
import com.prography.lighton.member.users.presentation.dto.response.GetMemberInfoResponse;
import com.prography.lighton.member.users.presentation.dto.response.GetMyPreferredArtistsResponse;
import com.prography.lighton.member.users.presentation.dto.response.GetPreferredGenreResponse;
import com.prography.lighton.performance.users.application.service.ArtistPerformanceService;
import com.prography.lighton.performance.users.application.service.UserPerformanceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserMemberQueryService {

    private final MemberRepository memberRepository;
    private final PreferredGenreRepository preferredGenreRepository;

    private final UserPerformanceService userPerformanceService;
    private final ArtistPerformanceService artistPerformanceService;

    public CheckDuplicateEmailResponse checkEmailExists(String email) {
        return CheckDuplicateEmailResponse.of(memberRepository.existsByEmail(email));
    }

    public GetPreferredGenreResponse getPreferredGenre(Member member) {
        List<Genre> genres = preferredGenreRepository.findAllByMember(member).stream()
                .map(PreferredGenre::getGenre)
                .toList();

        return GetPreferredGenreResponse.of(genres);
    }

    public GetMyPreferredArtistsResponse getMyPreferredArtists(Member member) {
        // 현재 구현은 내가 신청했던 공연의 아티스트 조회 - 추후 아티스트 페이지 개발 시 변경 예정
        List<Long> appliedPerformanceIds = userPerformanceService.getAppliedPerformances(member);
        if (appliedPerformanceIds.isEmpty()) {
            return GetMyPreferredArtistsResponse.of(List.of());
        }

        List<Artist> preferredArtists = artistPerformanceService.getArtistsByAppliedPerformanceIds(
                appliedPerformanceIds);
        return GetMyPreferredArtistsResponse.of(preferredArtists);
    }

    public GetMemberInfoResponse getMyInfo(Member member) {
        return GetMemberInfoResponse.of(member);
    }
}
