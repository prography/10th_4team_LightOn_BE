package com.prography.lighton.performance.common.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.fixture.BuskingFixture;
import com.prography.lighton.performance.common.domain.entity.fixture.PerformanceFixture;
import com.prography.lighton.performance.common.domain.entity.vo.Info;
import com.prography.lighton.performance.common.domain.entity.vo.Schedule;
import com.prography.lighton.performance.common.domain.exception.NotAuthorizedPerformanceException;
import com.prography.lighton.performance.common.domain.exception.PerformanceUpdateNotAllowedException;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("Busking 수정 테스트")
class BuskingUpdateTest {

    @Nested
    class ArtistTest {
        @Test
        @DisplayName("아티스트가 Busking 객체를 정상적으로 수정할 수 있다")
        void should_update_busking_by_artist() {
            Member performer = PerformanceFixture.defaultMember();
            Artist artist = PerformanceFixture.defaultArtist(performer);
            Busking b = Busking.createByArtist(
                    performer,
                    PerformanceFixture.defaultInfo(),
                    PerformanceFixture.defaultSchedule(),
                    PerformanceFixture.defaultLocation(),
                    PerformanceFixture.defaultGenres(),
                    PerformanceFixture.DEFAULT_PROOF_URL,
                    artist);

            Info updatedInfo = Info.of("수정 제목", "수정 설명", "수정 장소", "", "");

            b.updateByArtist(
                    performer,
                    updatedInfo, PerformanceFixture.defaultSchedule(), PerformanceFixture.defaultLocation(),
                    PerformanceFixture.defaultGenres(),
                    PerformanceFixture.DEFAULT_PROOF_URL
            );

            assertThat(b.getInfo()).isEqualTo(updatedInfo);
        }

        @Test
        @DisplayName("아티스트가 아닌 사용자가 Busking 수정 시 에러가 발생한다.")
        void should_throw_if_not_owner_update_by_artist() {
            Member performer = PerformanceFixture.defaultMember();
            Artist artist = PerformanceFixture.defaultArtist(performer);
            Busking b = BuskingFixture.defaultBuskingByArtist(performer, artist);

            Member notPerformer = mock(Member.class);

            assertThatThrownBy(() -> b.updateByArtist(
                    notPerformer,
                    b.getInfo(), b.getSchedule(), b.getLocation(),
                    PerformanceFixture.defaultGenres(), "수정 url"
            )).isInstanceOf(NotAuthorizedPerformanceException.class);
        }

        @Test
        @DisplayName("수정 기한 이후 아티스트 수정 시 에러가 발생한다.")
        void should_throw_update_by_artist_after_deadline() {
            Member performer = PerformanceFixture.defaultMember();
            Artist artist = PerformanceFixture.defaultArtist(performer);
            Busking b = BuskingFixture.defaultBuskingByArtist(performer, artist);
            Schedule near = Schedule.of(
                    LocalDate.now().plusDays(2), LocalDate.now().plusDays(3),
                    LocalTime.of(10, 0), LocalTime.of(12, 0)
            );
            ReflectionTestUtils.setField(b, "schedule", near);

            assertThatThrownBy(() -> b.updateByArtist(
                    b.getPerformer(), b.getInfo(), near, b.getLocation(),
                    PerformanceFixture.defaultGenres(), "수정 url"
            )).isInstanceOf(PerformanceUpdateNotAllowedException.class);
        }
    }

    @Nested
    class MemberTest {
        @Test
        @DisplayName("사용자가 Busking 객체를 정상적으로 수정할 수 있다")
        void should_update_busking_by_user() {
            Member performer = PerformanceFixture.defaultMember();
            Busking b = BuskingFixture.defaultBuskingByUser(performer);
            String newName = "NewName";
            String newDesc = "NewDesc";

            b.updateByUser(
                    b.getPerformer(),
                    PerformanceFixture.defaultInfo(),
                    PerformanceFixture.defaultSchedule(),
                    PerformanceFixture.defaultLocation(),
                    PerformanceFixture.defaultGenres(),
                    PerformanceFixture.DEFAULT_PROOF_URL,
                    newName, newDesc
            );

            assertThat(b.getArtistName()).isEqualTo(newName);
            assertThat(b.getArtistDescription()).isEqualTo(newDesc);
        }

        @Test
        @DisplayName("사용자가 아닌 다른 사람이 Busking 수정 시 에러가 발생한다.")
        void should_throw_if_not_owner_update_by_user() {
            Member performer = PerformanceFixture.defaultMember();
            Busking b = BuskingFixture.defaultBuskingByUser(performer);
            String newName = "NewName";
            String newDesc = "NewDesc";

            Member notPerformer = mock(Member.class);
            assertThatThrownBy(() -> b.updateByUser(
                    notPerformer,
                    b.getInfo(),
                    b.getSchedule(),
                    b.getLocation(),
                    PerformanceFixture.defaultGenres(),
                    PerformanceFixture.DEFAULT_PROOF_URL,
                    newName, newDesc
            )).isInstanceOf(NotAuthorizedPerformanceException.class);
        }

        @Test
        @DisplayName("수정 기한 이후 사용자가 Busking 수정 시 에러가 발생한다.")
        void should_throw_update_by_user_after_deadline() {
            Member performer = PerformanceFixture.defaultMember();
            Busking b = BuskingFixture.defaultBuskingByUser(performer);
            String newName = "NewName";
            String newDesc = "NewDesc";
            Schedule near = Schedule.of(
                    LocalDate.now().plusDays(2), LocalDate.now().plusDays(3),
                    LocalTime.of(10, 0), LocalTime.of(12, 0)
            );
            ReflectionTestUtils.setField(b, "schedule", near);

            assertThatThrownBy(() -> b.updateByUser(
                    performer,
                    b.getInfo(),
                    near,
                    b.getLocation(),
                    PerformanceFixture.defaultGenres(),
                    PerformanceFixture.DEFAULT_PROOF_URL,
                    newName, newDesc
            )).isInstanceOf(PerformanceUpdateNotAllowedException.class);
        }
    }
}
