package com.prography.lighton.performance.common.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.association.PerformanceArtist;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.common.domain.entity.enums.Seat;
import com.prography.lighton.performance.common.domain.entity.enums.Type;
import com.prography.lighton.performance.common.domain.entity.fixture.BuskingFixture;
import com.prography.lighton.performance.common.domain.entity.fixture.PerformanceFixture;
import com.prography.lighton.performance.common.domain.exception.MasterArtistCannotBeRemovedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BuskingCreateTest {

    @Nested
    class ArtistTest {
        @Test
        @DisplayName("아티스트가 Busking 객체를 정상적으로 생성할 수 있다")
        void should_create_busking_by_artist() {
            Member member = PerformanceFixture.defaultMember();
            Artist artist = PerformanceFixture.defaultArtist(member);

            Busking b = Busking.createByArtist(
                    member,
                    PerformanceFixture.defaultInfo(),
                    PerformanceFixture.defaultSchedule(),
                    PerformanceFixture.defaultLocation(),
                    PerformanceFixture.defaultGenres(),
                    PerformanceFixture.DEFAULT_PROOF_URL,
                    artist,
                    PerformanceFixture.ARTIST_NAME,
                    PerformanceFixture.ARTIST_DESCRIPTION);

            assertThat(b.getType()).isEqualTo(Type.BUSKING);
            assertThat(b.getPerformer().getId()).isEqualTo(PerformanceFixture.DEFAULT_PERFORMER_ID);
            assertThat(b.getInfo()).isEqualTo(PerformanceFixture.defaultInfo());
            assertThat(b.getSchedule()).isEqualTo(PerformanceFixture.defaultSchedule());
            assertThat(b.getLocation()).isNotNull();
            assertThat(b.getProofUrl()).isEqualTo(PerformanceFixture.DEFAULT_PROOF_URL);
        }

        @Test
        @DisplayName("아티스트 Busking 생성 시 Payment 는 free 이다")
        void should_set_payment_free() {
            Busking b = BuskingFixture.defaultBuskingByArtist();
            assertThat(b.getPayment().getIsPaid()).isFalse();
        }

        @Test
        @DisplayName("아티스트 Busking 생성 시 Seat 은 STANDING 이다")
        void should_set_seat_standing() {
            Busking b = BuskingFixture.defaultBuskingByArtist();
            assertThat(b.getSeats()).containsExactly(Seat.STANDING);
        }

        @Test
        @DisplayName("아티스트 Busking 객체 생성 시 공연은 바로 승인처리 된다.")
        void should_approve_busking_when_create_busking_by_artist() {
            Busking b = BuskingFixture.defaultBuskingByArtist();

            assertThat(b.getApproveStatus()).isEqualTo(ApproveStatus.APPROVED);
        }

        @Test
        @DisplayName("아티스트 Busking 생성 시 이름·설명은 Artist 엔티티 값이 들어간다")
        void should_set_artist_fields_from_artist_entity() {
            Member performer = PerformanceFixture.defaultMember();
            Artist artist = PerformanceFixture.defaultArtist(performer);
            String artistName = "name";
            String artistDescription = "description";
            when(artist.getStageName()).thenReturn(artistName);
            when(artist.getDescription()).thenReturn(artistDescription);

            Busking b = Busking.createByArtist(
                    performer,
                    PerformanceFixture.defaultInfo(),
                    PerformanceFixture.defaultSchedule(),
                    PerformanceFixture.defaultLocation(),
                    PerformanceFixture.defaultGenres(),
                    PerformanceFixture.DEFAULT_PROOF_URL,
                    artist,
                    PerformanceFixture.ARTIST_NAME,
                    PerformanceFixture.ARTIST_DESCRIPTION
            );

            assertThat(b.getArtistName()).isEqualTo(artistName);
            assertThat(b.getArtistDescription()).isEqualTo(artistDescription);
        }

        @Test
        @DisplayName("아티스트 Busking 생성 시 PerformanceArtist 리스트가 초기화된다")
        void should_init_performanceArtist_list() {
            Busking b = BuskingFixture.defaultBuskingByArtist();
            assertThat(b.getArtists())
                    .hasSize(1)
                    .allSatisfy(pa ->
                            assertThat(pa).isInstanceOf(PerformanceArtist.class)
                    );
        }

        @Test
        @DisplayName("performer와 artist가 일치하지 않으면 에러가 발생한다")
        void should_throw_when_performer_and_artist_is_not_same() {
            Member performer = PerformanceFixture.defaultMember();
            Member notPerformer = PerformanceFixture.defaultMember();
            Artist artist = PerformanceFixture.defaultArtist(notPerformer);
            when(performer.getId()).thenReturn(1L);
            when(notPerformer.getId()).thenReturn(2L);

            assertThatThrownBy(() -> Busking.createByArtist(
                    performer,
                    PerformanceFixture.defaultInfo(),
                    PerformanceFixture.defaultSchedule(),
                    PerformanceFixture.defaultLocation(),
                    PerformanceFixture.defaultGenres(),
                    PerformanceFixture.DEFAULT_PROOF_URL,
                    artist,
                    PerformanceFixture.ARTIST_NAME,
                    PerformanceFixture.ARTIST_DESCRIPTION
            )).isInstanceOf(MasterArtistCannotBeRemovedException.class);
        }
    }

    @Nested
    class MemberTest {
        @Test
        @DisplayName("사용자가 Busking 객체를 정상적으로 생성할 수 있다")
        void should_create_busking_by_user() {
            Busking b = BuskingFixture.defaultBuskingByUser();

            assertThat(b.getApproveStatus()).isEqualTo(ApproveStatus.PENDING);
            assertThat(b.getArtistName()).isEqualTo(BuskingFixture.ARTIST_NAME);
        }

        @Test
        @DisplayName("사용자 Busking 생성 시 Payment 는 free 이다")
        void should_set_payment_free() {
            Busking b = BuskingFixture.defaultBuskingByUser();
            assertThat(b.getPayment().getIsPaid()).isFalse();
        }

        @Test
        @DisplayName("사용자 Busking 생성 시 Seat 은 STANDING 이다")
        void should_set_seat_standing() {
            Busking b = BuskingFixture.defaultBuskingByUser();
            assertThat(b.getSeats()).containsExactly(Seat.STANDING);
        }
    }
}
