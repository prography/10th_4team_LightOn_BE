package com.prography.lighton.common.infrastructure.redis;

import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisZsetRepository {

    private final StringRedisTemplate redisTemplate;

    public Double incrementScore(String key, String member, double delta, Duration ttl) {
        Double score = redisTemplate.opsForZSet().incrementScore(key, member, delta);
        redisTemplate.expire(key, ttl);
        return score;
    }

    public void unionAndStore(String destKey, List<String> sourceKeys, Duration ttl) {
        if (sourceKeys.isEmpty()) {
            return;
        }
        String first = sourceKeys.getFirst();
        redisTemplate.opsForZSet()
                .unionAndStore(first, sourceKeys.subList(1, sourceKeys.size()), destKey);
        redisTemplate.expire(destKey, ttl);
    }

}
