package com.prography.lighton.member.common.domain.entity.association;

import static com.prography.lighton.common.fixture.MemberTestFixture.createNormalMember;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.common.domain.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PreferredGenreTest {

    @Test
    @DisplayName("PreferredGenre 객체가 올바르게 생성되는지 확인한다")
    void should_create_preferred_genre() {
        // Given
        Member member = createNormalMember(mock(PasswordEncoder.class));
        Genre genre = mock(Genre.class);

        // When
        PreferredGenre preferredGenre = PreferredGenre.of(member, genre);

        // Then
        assertEquals(member, preferredGenre.getMember());
        assertEquals(genre, preferredGenre.getGenre());
    }
}
