package com.prography.lighton.member.users.application.command;

import com.prography.lighton.genre.infrastructure.cache.GenreCache;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.domain.entity.association.PreferredGenre;
import com.prography.lighton.member.common.infrastructure.repository.PreferredGenreRepository;
import com.prography.lighton.member.users.presentation.dto.request.EditMemberGenreRequest;
import com.prography.lighton.performance.users.application.service.UserRecommendationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EditPreferredGenreService {

    private final PreferredGenreRepository preferredGenreRepository;

    private final GenreCache genreCache;
    private final UserRecommendationService userRecommendationService;

    public void handle(Member member, EditMemberGenreRequest request) {
        deletePreviousPreferredGenres(member);

        List<PreferredGenre> preferredGenres = genreCache.getGenresByNameOrThrow(request.genres()).stream()
                .map((genre -> PreferredGenre.of(member, genre)))
                .toList();
        member.editPreferredGenres(preferredGenres);

        preferredGenreRepository.saveAll(preferredGenres);
    }

    private void deletePreviousPreferredGenres(Member member) {
        preferredGenreRepository.deleteAllByMember(member);
        userRecommendationService.deleteCache(member);
    }
}
