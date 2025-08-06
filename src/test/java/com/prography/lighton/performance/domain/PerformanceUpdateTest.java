package com.prography.lighton.performance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.common.domain.entity.vo.Info;
import com.prography.lighton.performance.common.domain.entity.vo.Schedule;
import com.prography.lighton.performance.common.domain.exception.InvalidSeatCountException;
import com.prography.lighton.performance.common.domain.exception.MasterArtistCannotBeRemovedException;
import com.prography.lighton.performance.common.domain.exception.NotAuthorizedPerformanceException;
import com.prography.lighton.performance.common.domain.exception.PerformanceUpdateNotAllowedException;
import com.prography.lighton.performance.domain.fixture.PerformanceFixture;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class PerformanceUpdateTest {

    @Nested
    class UpdateTest {
        @Test
        @DisplayName("Performance 객체를 정상적으로 수정할 수 있다.")
        void should_update_performance() {
            // give
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

            Info updatedInfo = Info.of("수정 제목", "수정 설명", "수정 장소", "", "");
            p.update(
                    PerformanceFixture.defaultMember(),
                    PerformanceFixture.defaultArtists(PerformanceFixture.defaultMember()),
                    updatedInfo,
                    PerformanceFixture.defaultSchedule(),
                    PerformanceFixture.defaultLocation(),
                    PerformanceFixture.defaultPayment(),
                    PerformanceFixture.defaultSeats(),
                    PerformanceFixture.defaultGenres(),
                    PerformanceFixture.DEFAULT_PROOF_URL,
                    PerformanceFixture.DEFAULT_TOTAL_SEATS
            );

            // then
            assertThat(p.getInfo()).isEqualTo(updatedInfo);
        }

        @Test
        @DisplayName("공연 등록자가 아닌 사람이 performance를 수정하려고 하면 에러를 발생한다.")
        void should_throw_if_not_performer() {
            Performance p = PerformanceFixture.defaultPerformance();
            assertThatThrownBy(() ->
                    p.update(
                            mock(PerformanceFixture.defaultMember().getClass()),
                            PerformanceFixture.defaultArtists(PerformanceFixture.defaultMember()),
                            PerformanceFixture.defaultInfo(),
                            PerformanceFixture.defaultSchedule(),
                            PerformanceFixture.defaultLocation(),
                            PerformanceFixture.defaultPayment(),
                            PerformanceFixture.defaultSeats(),
                            PerformanceFixture.defaultGenres(),
                            PerformanceFixture.DEFAULT_PROOF_URL,
                            PerformanceFixture.DEFAULT_TOTAL_SEATS
                    )
            ).isInstanceOf(NotAuthorizedPerformanceException.class);
        }

        @Test
        @DisplayName("공연 수정 가능 일시가 지난 시점에서 performance를 수정하려고 하면 에러를 발생한다.")
        void should_throw_if_after_update_deadline() {
            // given
            Schedule nearSched = Schedule.of(
                    LocalDate.now().plusDays(2),
                    LocalDate.now().plusDays(3),
                    LocalTime.of(9, 0),
                    LocalTime.of(18, 0)
            );
            Performance p = PerformanceFixture.defaultPerformance();
            ReflectionTestUtils.setField(p, "schedule", nearSched);

            assertThatThrownBy(() ->
                    p.update(
                            PerformanceFixture.defaultMember(),
                            PerformanceFixture.defaultArtists(PerformanceFixture.defaultMember()),
                            PerformanceFixture.defaultInfo(),
                            nearSched,
                            PerformanceFixture.defaultLocation(),
                            PerformanceFixture.defaultPayment(),
                            PerformanceFixture.defaultSeats(),
                            PerformanceFixture.defaultGenres(),
                            PerformanceFixture.DEFAULT_PROOF_URL,
                            PerformanceFixture.DEFAULT_TOTAL_SEATS
                    )
            ).isInstanceOf(PerformanceUpdateNotAllowedException.class);
        }

        @Test
        @DisplayName("공연 증빙자료 url이 빈 값이라면 에러가 발생한다.")
        void should_throw_if_proofUrl_blank() {
            Performance p = PerformanceFixture.defaultPerformance();
            assertThatThrownBy(() ->
                    p.update(
                            PerformanceFixture.defaultMember(),
                            PerformanceFixture.defaultArtists(PerformanceFixture.defaultMember()),
                            PerformanceFixture.defaultInfo(),
                            PerformanceFixture.defaultSchedule(),
                            PerformanceFixture.defaultLocation(),
                            PerformanceFixture.defaultPayment(),
                            PerformanceFixture.defaultSeats(),
                            PerformanceFixture.defaultGenres(),
                            " ",
                            PerformanceFixture.DEFAULT_TOTAL_SEATS
                    )
            ).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("총 좌석 수가 예약 좌석 수 미만이면 에러가 발생한다")
        void should_throw_if_totalSeats_less_than_bookedCount() {
            Performance p = PerformanceFixture.defaultPerformance();
            p.managePerformanceApplication(ApproveStatus.APPROVED);

            int applySeatCount = 5;
            int updateSeatCount = applySeatCount - 1;
            p.createRequest(applySeatCount, PerformanceFixture.defaultMember());

            assertThatThrownBy(() ->
                    p.update(
                            PerformanceFixture.defaultMember(),
                            PerformanceFixture.defaultArtists(PerformanceFixture.defaultMember()),
                            PerformanceFixture.defaultInfo(),
                            PerformanceFixture.defaultSchedule(),
                            PerformanceFixture.defaultLocation(),
                            PerformanceFixture.defaultPayment(),
                            PerformanceFixture.defaultSeats(),
                            PerformanceFixture.defaultGenres(),
                            PerformanceFixture.DEFAULT_PROOF_URL,
                            updateSeatCount
                    )
            ).isInstanceOf(InvalidSeatCountException.class);
        }

        @Test
        @DisplayName("공연 등록자를 공연 아티스트에서 삭제하면 에러가 발생한다")
        void should_throw_if_removing_performer() {
            // given
            Member performer = PerformanceFixture.defaultMember();
            Artist master = PerformanceFixture.defaultArtist(performer);
            Artist a2 = mock(Artist.class);
            when(a2.getId()).thenReturn(99L);
            when(a2.getMember()).thenReturn(performer);
            Performance p = Performance.create(
                    performer,
                    List.of(master, a2),
                    PerformanceFixture.defaultInfo(),
                    PerformanceFixture.defaultSchedule(),
                    PerformanceFixture.defaultLocation(),
                    PerformanceFixture.defaultPayment(),
                    PerformanceFixture.defaultSeats(),
                    PerformanceFixture.defaultGenres(),
                    PerformanceFixture.DEFAULT_PROOF_URL,
                    PerformanceFixture.DEFAULT_TOTAL_SEATS
            );

            // when: 마스터 아티스트(공연 등록자) 제거
            assertThatThrownBy(() ->
                    p.update(
                            performer,
                            List.of(a2),
                            PerformanceFixture.defaultInfo(),
                            PerformanceFixture.defaultSchedule(),
                            PerformanceFixture.defaultLocation(),
                            PerformanceFixture.defaultPayment(),
                            PerformanceFixture.defaultSeats(),
                            PerformanceFixture.defaultGenres(),
                            PerformanceFixture.DEFAULT_PROOF_URL,
                            PerformanceFixture.DEFAULT_TOTAL_SEATS)
            ).isInstanceOf(MasterArtistCannotBeRemovedException.class);
        }

        @Test
        @DisplayName("기존 공연 아티스트가 삭제되면 PerformanceArtist도 삭제된다")
        void should_remove_performanceArtist_when_artist_removed() {
            // given: a1, a2 추가
            Member performer = PerformanceFixture.defaultMember();
            Artist a1 = PerformanceFixture.defaultArtist(performer);
            Artist a2 = mock(Artist.class);
            when(a2.getId()).thenReturn(99L);
            when(a2.getMember()).thenReturn(performer);
            Performance p = Performance.create(
                    performer,
                    List.of(a1, a2),
                    PerformanceFixture.defaultInfo(),
                    PerformanceFixture.defaultSchedule(),
                    PerformanceFixture.defaultLocation(),
                    PerformanceFixture.defaultPayment(),
                    PerformanceFixture.defaultSeats(),
                    PerformanceFixture.defaultGenres(),
                    PerformanceFixture.DEFAULT_PROOF_URL,
                    PerformanceFixture.DEFAULT_TOTAL_SEATS
            );

            // when: a2 제거
            p.update(
                    performer,
                    List.of(a1),
                    PerformanceFixture.defaultInfo(),
                    PerformanceFixture.defaultSchedule(),
                    PerformanceFixture.defaultLocation(),
                    PerformanceFixture.defaultPayment(),
                    PerformanceFixture.defaultSeats(),
                    PerformanceFixture.defaultGenres(),
                    PerformanceFixture.DEFAULT_PROOF_URL,
                    PerformanceFixture.DEFAULT_TOTAL_SEATS
            );

            // then
            assertThat(p.getArtists()).hasSize(1)
                    .extracting(pa -> pa.getArtist().getId())
                    .containsExactly(a1.getId());
        }

        @Test
        @DisplayName("공연 아티스트가 추가되면 PerformanceArtist도 추가된다")
        void should_add_performanceArtist_when_artist_added() {
            // given
            Member performer = PerformanceFixture.defaultMember();
            Artist existing = PerformanceFixture.defaultArtist(performer);
            Performance p = PerformanceFixture.defaultPerformance();

            // when: 새로운 아티스트 추가
            Artist newArtist = mock(Artist.class);
            when(newArtist.getId()).thenReturn(200L);
            when(newArtist.getMember()).thenReturn(performer);
            p.update(
                    performer,
                    List.of(existing, newArtist),
                    PerformanceFixture.defaultInfo(),
                    PerformanceFixture.defaultSchedule(),
                    PerformanceFixture.defaultLocation(),
                    PerformanceFixture.defaultPayment(),
                    PerformanceFixture.defaultSeats(),
                    PerformanceFixture.defaultGenres(),
                    PerformanceFixture.DEFAULT_PROOF_URL,
                    PerformanceFixture.DEFAULT_TOTAL_SEATS
            );

            assertThat(p.getArtists()).hasSize(2)
                    .extracting(pa -> pa.getArtist().getId())
                    .contains(existing.getId(), newArtist.getId());
        }

        @Test
        @DisplayName("기존 공연 장르가 삭제되면 PerformanceGenre도 삭제된다")
        void should_remove_performanceGenre_when_genre_removed() {
            // given
            Member performer = PerformanceFixture.defaultMember();
            Genre g1 = PerformanceFixture.defaultGenre();
            Genre g2 = mock(Genre.class);
            when(g2.getId()).thenReturn(300L);
            Performance p = Performance.create(
                    performer,
                    PerformanceFixture.defaultArtists(performer),
                    PerformanceFixture.defaultInfo(),
                    PerformanceFixture.defaultSchedule(),
                    PerformanceFixture.defaultLocation(),
                    PerformanceFixture.defaultPayment(),
                    PerformanceFixture.defaultSeats(),
                    List.of(g1, g2),
                    PerformanceFixture.DEFAULT_PROOF_URL,
                    PerformanceFixture.DEFAULT_TOTAL_SEATS
            );

            // when: g2 제거
            p.update(
                    performer,
                    PerformanceFixture.defaultArtists(performer),
                    PerformanceFixture.defaultInfo(),
                    PerformanceFixture.defaultSchedule(),
                    PerformanceFixture.defaultLocation(),
                    PerformanceFixture.defaultPayment(),
                    PerformanceFixture.defaultSeats(),
                    List.of(g1),
                    PerformanceFixture.DEFAULT_PROOF_URL,
                    PerformanceFixture.DEFAULT_TOTAL_SEATS
            );

            assertThat(p.getGenres()).hasSize(1)
                    .extracting(pg -> pg.getGenre().getId())
                    .containsExactly(g1.getId());
        }

        @Test
        @DisplayName("공연 장르가 추가되면 PerformanceGenre도 추가된다")
        void should_add_performanceGenre_when_genre_added() {
            // given
            Member performer = PerformanceFixture.defaultMember();
            Genre existing = PerformanceFixture.defaultGenre();
            Performance p = PerformanceFixture.defaultPerformance();

            // when: 새로운 장르 추가
            Genre newGenre = mock(Genre.class);
            when(newGenre.getId()).thenReturn(400L);
            p.update(
                    performer,
                    PerformanceFixture.defaultArtists(performer),
                    PerformanceFixture.defaultInfo(),
                    PerformanceFixture.defaultSchedule(),
                    PerformanceFixture.defaultLocation(),
                    PerformanceFixture.defaultPayment(),
                    PerformanceFixture.defaultSeats(),
                    List.of(existing, newGenre),
                    PerformanceFixture.DEFAULT_PROOF_URL,
                    PerformanceFixture.DEFAULT_TOTAL_SEATS
            );

            assertThat(p.getGenres()).hasSize(2)
                    .extracting(pg -> pg.getGenre().getId())
                    .contains(existing.getId(), newGenre.getId());
        }
    }

    @Nested
    class CanceledTest {

        @Test
        @DisplayName("공연 취소를 정상적으로 할 수 있다.")
        void should_cancel_performance() {
            // give
            Member performer = PerformanceFixture.defaultMember();
            Performance p = PerformanceFixture.defaultPerformance(performer);

            p.cancel(performer);

            // then
            assertThat(p.isCanceled()).isTrue();
        }

        @Test
        @DisplayName("공연 등록자가 아닌 사람이 공연을 취소하려고 하면 에러가 발생한다.")
        void should_throw_if_not_performer() {
            Performance p = PerformanceFixture.defaultPerformance();
            assertThatThrownBy(() ->
                    p.cancel(mock(PerformanceFixture.defaultMember().getClass()))
            ).isInstanceOf(NotAuthorizedPerformanceException.class);
        }

        @Test
        @DisplayName("공연 취소 가능 일시가 지난 시점에 공연을 취소하려고 하면 에러가 발생한다.")
        void should_throw_if_after_update_deadline() {
            // given
            Schedule nearSched = Schedule.of(
                    LocalDate.now(),
                    LocalDate.now().plusDays(3),
                    LocalTime.of(9, 0),
                    LocalTime.of(18, 0)
            );
            Member performer = PerformanceFixture.defaultMember();
            Performance p = PerformanceFixture.defaultPerformance();
            ReflectionTestUtils.setField(p, "schedule", nearSched);

            assertThatThrownBy(() ->
                    p.cancel(performer)
            ).isInstanceOf(PerformanceUpdateNotAllowedException.class);
        }

        @Test
        @DisplayName("이미 취소된 공연을 다시 취소하려고 하면 에러가 발생한다.")
        void should_throw_if_recancel_performance() {
            Member performer = PerformanceFixture.defaultMember();
            Performance p = PerformanceFixture.defaultPerformance(performer);
            ReflectionTestUtils.setField(p, "canceled", true);

            assertThatThrownBy(() ->
                    p.cancel(performer)
            ).isInstanceOf(NotAuthorizedPerformanceException.class);
        }
    }

    @Nested
    class LikeTest {
        @Test
        @DisplayName("공연 좋아요가 정상적으로 증가한다.")
        void should_increase_like_count() {
            Performance p = PerformanceFixture.defaultPerformance();

            long before = p.getLikeCount();
            p.increaseLike();
            long after = p.getLikeCount();

            assertThat(after).isEqualTo(before + 1);
        }
        
        @Test
        @DisplayName("공연 좋아요가 정상적으로 감소한다.")
        void should_decrease_like_count() {
            Performance p = PerformanceFixture.defaultPerformance();

            long before = p.getLikeCount();
            p.decreaseLike();
            long after = p.getLikeCount();

            assertThat(after).isEqualTo(before - 1);
        }
    }
}
