package com.prography.lighton.artist.users.application.service;

import com.prography.lighton.common.infrastructure.redis.RedisZsetRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
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

    public List<ArtistRankDto> topArtistsLast14Days(int topN) {
        List<String> dayKeys = IntStream.range(0, 14)
                .mapToObj(i -> buildKey(LocalDate.now().minusDays(i)))
                .toList();

        String tmpKey = "artist:likes:z:sum14d";
        redisZsetRepository.unionAndStore(tmpKey, dayKeys, TMP_TTL);

        Set<TypedTuple<String>> tuples =
                redisZsetRepository.reverseRangeWithScores(tmpKey, 0, topN - 1);

        if (tuples == null || tuples.isEmpty()) {
            return List.of();
        }

        return tuples.stream()
                .map(t -> new ArtistRankDto(
                        Long.valueOf(t.getValue()),
                        t.getScore().longValue()))
                .toList();
    }

    private String buildKey(LocalDate date) {
        return "artist:likes:z:" + date.format(DTF);
    }

    public record ArtistRankDto(Long artistId, Long likeCount) {
    }
}
