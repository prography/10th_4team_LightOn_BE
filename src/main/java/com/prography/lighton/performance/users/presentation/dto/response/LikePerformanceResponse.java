package com.prography.lighton.performance.users.presentation.dto.response;

public record LikePerformanceResponse(boolean nowLiked) {

    public static LikePerformanceResponse of(boolean nowLiked) {
        return new LikePerformanceResponse(nowLiked);
    }
}
