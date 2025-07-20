package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.common.infrastructure.redis.RedisZsetRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtistLikeRedisService {

    private static final Duration ZSET_TTL = Duration.ofDays(15);
    private static final Duration TMP_TTL = Duration.ofSeconds(30);
    private static final DateTimeFormatter DTF = DateTimeFormatter.BASIC_ISO_DATE;

    private final RedisZsetRepository redisZsetRepository;

    public void incrementToday(Long artistId) {
        String todayKey = buildKey(LocalDate.now());
        redisZsetRepository.incrementScore(todayKey, artistId.toString(), 1, ZSET_TTL);
    }


    private String buildKey(LocalDate date) {
        return "artist:likes:z:" + date.format(DTF);
    }

    public record ArtistRankDto(Long artistId, Long likeCount) {
    }
}
