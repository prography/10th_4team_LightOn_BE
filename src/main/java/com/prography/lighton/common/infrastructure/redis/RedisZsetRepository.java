package com.prography.lighton.common.infrastructure.redis;

import java.time.Duration;
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

}
