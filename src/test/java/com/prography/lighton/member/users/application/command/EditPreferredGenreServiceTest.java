package com.prography.lighton.member.users.application.command;

import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.verify;

import com.prography.lighton.common.fixture.MemberTestFixture;
import com.prography.lighton.genre.infrastructure.cache.GenreCache;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.infrastructure.repository.PreferredGenreRepository;
import com.prography.lighton.member.users.presentation.dto.request.EditMemberGenreRequest;
import com.prography.lighton.performance.users.application.service.UserRecommendationService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EditPreferredGenreServiceTest {

    @Mock
    private PreferredGenreRepository preferredGenreRepository;

    @Mock
    private GenreCache genreCache;

    @Mock
    private UserRecommendationService userRecommendationService;

    @Test
    @DisplayName("회원의 선호 장르를 수정할 수 있다.")
    void should_edit_member_preferred_genre() {
        // Given
        EditPreferredGenreService service = new EditPreferredGenreService(preferredGenreRepository, genreCache,
                userRecommendationService);
        EditMemberGenreRequest req = new EditMemberGenreRequest(List.of("팝", "록"));
        Member member = MemberTestFixture.createNormalMember();

        // When
        service.handle(member, req);

        // Then
        verify(preferredGenreRepository).deleteAllByMember(member);
        verify(genreCache).getGenresByNameOrThrow(req.genres());
        verify(preferredGenreRepository).saveAll(anyCollection());
    }

}