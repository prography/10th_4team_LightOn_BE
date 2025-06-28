package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.common.infrastructure.redis.RedisRepository;
import io.micrometer.common.util.StringUtils;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRecommendationRedisService {

    private final RedisRepository redisRepository;

    public List<Long> getRecommendPerformanceIds(Long memberId) {
        String raw = redisRepository.find(key(memberId));
        return raw == null ? null
                : Arrays.stream(raw.split(","))
                        .filter(StringUtils::isNotBlank)
                        .map(Long::valueOf)
                        .toList();
    }

    public void putRecommendPerformanceIds(Long memberId, List<Long> performanceIds) {
        if (performanceIds.isEmpty()) {
            return;
        }

        String value = performanceIds.stream()
                .limit(50)
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        Duration ttl = Duration.between(
                LocalDateTime.now(ZoneId.of("Asia/Seoul")),
                LocalDate.now(ZoneId.of("Asia/Seoul")).atTime(23, 59, 59));

        redisRepository.save(key(memberId), value, ttl);
    }

    private String key(Long memberId) {
        return "recommend:" + memberId;
    }
}
