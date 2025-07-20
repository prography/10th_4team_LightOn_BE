package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.common.infrastructure.redis.RedisRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtistLikeRedisService {

    private static final Duration TTL_14_DAYS = Duration.ofDays(14);
    private final RedisRepository redis;

    public void incrementToday(Long artistId) {
        String key = buildKey(artistId, LocalDate.now());
        redis.increment(key, 1, TTL_14_DAYS);
    }

    private String buildKey(Long artistId, LocalDate date) {
        return "artist:likes:%d:%s".formatted(
                artistId, date.format(DateTimeFormatter.BASIC_ISO_DATE));
    }
}
