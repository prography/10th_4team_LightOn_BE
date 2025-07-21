package com.prography.lighton.common.infrastructure.redis;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisRepository {

    private final StringRedisTemplate redisTemplate;

    public void save(String key, String value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    public String find(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public Long increment(String key, long delta, Duration ttl) {
        Long value = redisTemplate.opsForValue().increment(key, delta);
        if (redisTemplate.getExpire(key) == -1) {
            redisTemplate.expire(key, ttl);
        }
        return value;
    }

    public List<String> multiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }
}
