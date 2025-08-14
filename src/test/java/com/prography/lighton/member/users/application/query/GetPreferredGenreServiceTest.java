package com.prography.lighton.member.users.application.query;

import static com.prography.lighton.common.fixture.MemberTestFixture.createNormalMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.domain.entity.association.PreferredGenre;
import com.prography.lighton.member.common.infrastructure.repository.PreferredGenreRepository;
import com.prography.lighton.member.users.presentation.dto.response.GetPreferredGenreResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetPreferredGenreServiceTest {

    @Mock
    PreferredGenreRepository preferredGenreRepository;
    Member member;

    @Test
    @DisplayName("회원의 선호 장르를 조회할 수 있다.")
    void should_get_member_preferred_genre() {
        // Given
        GetPreferredGenreService service = new GetPreferredGenreService(preferredGenreRepository);

        member = createNormalMember();

        PreferredGenre preferredGenre1 = createPreferredGenre(createGenre("팝"));
        PreferredGenre preferredGenre2 = createPreferredGenre(createGenre("록"));

        when(preferredGenreRepository.findAllByMember(member)).thenReturn(
                List.of(preferredGenre1, preferredGenre2)
        );
        // When
        GetPreferredGenreResponse response = service.handle(member);

        // Then
        verify(preferredGenreRepository).findAllByMember(any());

        assertThat(response.genres()).hasSize(2);
        assertThat(response.genres().getFirst().name()).isEqualTo("팝");
    }

    @Test
    @DisplayName("회원의 선호 장르가 없는 경우 빈 리스트를 반환한다.")
    void should_return_empty_list_when_no_preferred_genres() {
        // Given
        GetPreferredGenreService service = new GetPreferredGenreService(preferredGenreRepository);
        member = createNormalMember();

        when(preferredGenreRepository.findAllByMember(member)).thenReturn(List.of());

        // When
        GetPreferredGenreResponse response = service.handle(member);

        // Then
        verify(preferredGenreRepository).findAllByMember(any());
        assertThat(response.genres()).isEmpty();
    }

    private Genre createGenre(String name) {
        return Genre.of(name, "imageUrl");
    }

    private PreferredGenre createPreferredGenre(Genre genre) {
        return PreferredGenre.of(member, genre);
    }

}
