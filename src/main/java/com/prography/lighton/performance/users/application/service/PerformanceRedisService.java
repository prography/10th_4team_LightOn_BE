package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.common.infrastructure.redis.RedisRepository;
import io.micrometer.common.util.StringUtils;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PerformanceRedisService {

    private final RedisRepository redisRepository;

    private Duration ttlToday() {
        return Duration.between(
                LocalDateTime.now(ZoneId.of("Asia/Seoul")),
                LocalDate.now(ZoneId.of("Asia/Seoul")).atTime(23, 59, 59));
    }

    public List<Long> get(String key) {
        String raw = redisRepository.find(key);
        return raw == null ? null
                : Arrays.stream(raw.split(","))
                        .filter(StringUtils::isNotBlank)
                        .map(Long::valueOf)
                        .toList();
    }

    public void put(String key, List<Long> ids) {
        if (ids.isEmpty()) {
            return;
        }
        String csv = String.join(",", ids.stream()
                .map(String::valueOf).toList());
        redisRepository.save(key, csv, ttlToday());
    }

    public void delete(String key) {
        redisRepository.delete(key);
    }
}
