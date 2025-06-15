package com.prography.lighton.performance.users.presentation.dto.response;

public record LikePerformanceResponse(boolean isLiked) {
    
    public static LikePerformanceResponse of(boolean isLiked) {
        return new LikePerformanceResponse(isLiked);
    }
}
