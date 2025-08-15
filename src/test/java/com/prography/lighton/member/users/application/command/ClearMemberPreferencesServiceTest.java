package com.prography.lighton.member.users.application.command;

import static org.mockito.Mockito.verify;

import com.prography.lighton.common.fixture.MemberTestFixture;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.infrastructure.repository.PreferredArtistRepository;
import com.prography.lighton.member.common.infrastructure.repository.PreferredGenreRepository;
import com.prography.lighton.performance.users.application.service.UserRecommendationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClearMemberPreferencesServiceTest {

    @Mock
    private PreferredGenreRepository preferredGenreRepository;

    @Mock
    private PreferredArtistRepository preferredArtistRepository;

    @Mock
    private UserRecommendationService userRecommendationService;


    @Test
    @DisplayName("회원 비활성화 시 기존 선호 장르를 삭제한다.")
    void should_delete_previous_preferred_genres_when_member_inactivated() {
        // Given
        ClearMemberPreferencesService service = new ClearMemberPreferencesService(preferredGenreRepository,
                preferredArtistRepository, userRecommendationService);
        Member member = MemberTestFixture.createNormalMember();

        // When
        service.handle(member);

        // Then
        verify(preferredGenreRepository).deleteAllByMember(member);
        verify(userRecommendationService).deleteCache(member);
    }
}
