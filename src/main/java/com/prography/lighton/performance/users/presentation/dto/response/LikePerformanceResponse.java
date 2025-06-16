package com.prography.lighton.performance.users.presentation.dto.response;

public record LikePerformanceResponse(boolean isNowLiked) {

    public static LikePerformanceResponse of(boolean isNowLiked) {
        return new LikePerformanceResponse(isNowLiked);
    }
}
