package com.prography.lighton.performance.common.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.prography.lighton.member.common.domain.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PerformanceLikeTest {

    @Test
    @DisplayName("of() 메서드로 liked 상태를 설정할 수 있다")
    void should_set_liked_state_from_of() {
        // given
        Member member = mock(Member.class);
        Performance performance = mock(Performance.class);

        // when
        PerformanceLike likeTrue = PerformanceLike.of(member, performance, true);
        PerformanceLike likeFalse = PerformanceLike.of(member, performance, false);

        // then
        assertThat(likeTrue.isLiked()).isTrue();
        assertThat(likeFalse.isLiked()).isFalse();
    }

    @Test
    @DisplayName("좋아요 여부가 true일 때 toggleLike() 호출 시 좋아요 여부가 false로 전환되고 performance.decreaseLike()가 호출된다")
    void should_toggle_to_false_and_decrease_like() {
        // given
        Member member = mock(Member.class);
        Performance performance = mock(Performance.class);
        PerformanceLike like = PerformanceLike.of(member, performance, true);

        // when
        boolean newState = like.toggleLike();

        // then
        assertThat(newState).isFalse();
        assertThat(like.isLiked()).isFalse();
        verify(performance).decreaseLike();
        verify(performance, never()).increaseLike();
    }

    @Test
    @DisplayName("좋아요 여부가 false 때 toggleLike() 호출 시 좋아요 여부가 true로 전환되고 performance.increaseLike()가 호출된다")
    void should_toggle_to_true_and_increase_like() {
        // given
        Member member = mock(Member.class);
        Performance performance = mock(Performance.class);
        PerformanceLike like = PerformanceLike.of(member, performance, false);

        // when
        boolean newState = like.toggleLike();

        // then
        assertThat(newState).isTrue();
        assertThat(like.isLiked()).isTrue();
        verify(performance).increaseLike();
        verify(performance, never()).decreaseLike();
    }

    @Test
    @DisplayName("toggleLike()를 두 번 호출하면 liked 상태가 원래 상태로 복귀한다")
    void should_toggle_back_to_original_state() {
        // given
        Member member = mock(Member.class);
        Performance performance = mock(Performance.class);
        PerformanceLike like = PerformanceLike.of(member, performance, true);

        // when
        boolean firstToggle = like.toggleLike();
        boolean secondToggle = like.toggleLike();

        // then
        assertThat(firstToggle).isFalse();
        assertThat(secondToggle).isTrue();
        verify(performance).decreaseLike();
        verify(performance).increaseLike();
    }
}

