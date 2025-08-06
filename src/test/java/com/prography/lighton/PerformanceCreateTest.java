package com.prography.lighton;

import static org.assertj.core.api.Assertions.assertThat;

import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.association.PerformanceArtist;
import com.prography.lighton.performance.common.domain.entity.association.PerformanceGenre;
import com.prography.lighton.performance.common.domain.entity.enums.Type;
import com.prography.lighton.performance.domain.fixture.PerformanceFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PerformanceCreateTest {

    @Test
    @DisplayName("Performance 객체를 정상적으로 생성할 수 있다.")
    void should_create_performance() {
        Performance p = Performance.create(
                PerformanceFixture.defaultMember(),
                PerformanceFixture.defaultArtists(PerformanceFixture.defaultMember()),
                PerformanceFixture.defaultInfo(),
                PerformanceFixture.defaultSchedule(),
                PerformanceFixture.defaultLocation(),
                PerformanceFixture.defaultPayment(),
                PerformanceFixture.defaultSeats(),
                PerformanceFixture.defaultGenres(),
                PerformanceFixture.DEFAULT_PROOF_URL,
                PerformanceFixture.DEFAULT_TOTAL_SEATS
        );

        assertThat(p.getPerformer().getId()).isEqualTo(PerformanceFixture.DEFAULT_PERFORMER_ID);
        assertThat(p.getInfo()).isEqualTo(PerformanceFixture.defaultInfo());
        assertThat(p.getSchedule()).isEqualTo(PerformanceFixture.defaultSchedule());
        assertThat(p.getLocation()).isNotNull();
        assertThat(p.getPayment()).isEqualTo(PerformanceFixture.defaultPayment());
        assertThat(p.getType()).isEqualTo(Type.CONCERT);
        assertThat(p.getSeats()).containsExactlyElementsOf(PerformanceFixture.defaultSeats());
        assertThat(p.getProofUrl()).isEqualTo(PerformanceFixture.DEFAULT_PROOF_URL);
        assertThat(p.getTotalSeatsCount()).isEqualTo(PerformanceFixture.DEFAULT_TOTAL_SEATS);
    }

    @Test
    @DisplayName("Performance 생성 시 PerformanceArtist 리스트가 초기화된다")
    void should_initialize_artists() {
        Performance p = PerformanceFixture.defaultPerformance();
        List<PerformanceArtist> artists = p.getArtists();

        assertThat(artists).hasSize(1)
                .allSatisfy(pa -> assertThat(pa.getArtist().getId())
                        .isEqualTo(PerformanceFixture.defaultArtist(PerformanceFixture.defaultMember()).getId()));
    }

    @Test
    @DisplayName("Performance 생성 시 PerformanceGenre 리스트가 초기화된다")
    void should_initialize_genres() {
        Performance p = PerformanceFixture.defaultPerformance();
        List<PerformanceGenre> genres = p.getGenres();

        assertThat(genres).hasSize(1)
                .allSatisfy(pg -> assertThat(pg.getGenre().getId())
                        .isEqualTo(PerformanceFixture.defaultGenre().getId()));
    }
}
