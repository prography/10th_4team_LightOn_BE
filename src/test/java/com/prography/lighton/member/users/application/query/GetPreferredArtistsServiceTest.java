package com.prography.lighton.member.users.application.query;

import static com.prography.lighton.common.fixture.ArtistTestFixture.createArtist;
import static com.prography.lighton.common.fixture.MemberTestFixture.createNormalMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.users.presentation.dto.response.GetMyPreferredArtistsResponse;
import com.prography.lighton.performance.users.application.service.ArtistPerformanceService;
import com.prography.lighton.performance.users.application.service.UserPerformanceService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetPreferredArtistsServiceTest {

    @Mock
    UserPerformanceService userPerformanceService;

    @Mock
    ArtistPerformanceService artistPerformanceService;

    @Test
    @DisplayName("선호 아티스트를 조회할 수 있다.")
    void should_get_preferred_artists() {
        // Given
        GetPreferredArtistsService service = new GetPreferredArtistsService(userPerformanceService,
                artistPerformanceService);
        Member member = createNormalMember();

        Artist artist1 = createArtist(1L);
        Artist artist2 = createArtist(2L);

        when(userPerformanceService.getAppliedPerformances(member)).thenReturn(List.of(1L, 2L, 3L));
        when(artistPerformanceService.getArtistsByAppliedPerformanceIds(List.of(1L, 2L, 3L)))
                .thenReturn(List.of(artist1, artist2));

        // When
        GetMyPreferredArtistsResponse response = service.handle(member);

        // Then
        verify(userPerformanceService).getAppliedPerformances(any());
        verify(artistPerformanceService).getArtistsByAppliedPerformanceIds(anyList());
        assertThat(response.artists().size()).isEqualTo(2);
        assertThat(response.artists().getFirst().id()).isEqualTo(1L);
    }

    @Test
    @DisplayName("아티스트가 없는 경우 빈 리스트를 반환한다.")
    void should_return_empty_list_when_no_artists() {
        // Given
        GetPreferredArtistsService service = new GetPreferredArtistsService(userPerformanceService,
                artistPerformanceService);
        Member member = createNormalMember();

        when(userPerformanceService.getAppliedPerformances(member)).thenReturn(List.of());

        // When
        GetMyPreferredArtistsResponse response = service.handle(member);

        // Then
        verify(userPerformanceService).getAppliedPerformances(any());
        assertThat(response.artists()).isEmpty();
    }
}