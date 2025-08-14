package com.prography.lighton.member.users.application.command;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.infrastructure.repository.PreferredGenreRepository;
import com.prography.lighton.performance.users.application.service.UserRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InactivateMemberService {

    private final PreferredGenreRepository preferredGenreRepository;
    private final UserRecommendationService userRecommendationService;

    public void handle(Member member) {
        deletePreviousPreferredGenres(member);
    }

    private void deletePreviousPreferredGenres(Member member) {
        preferredGenreRepository.deleteAllByMember(member);
        userRecommendationService.deleteCache(member);
    }

}