package com.prography.lighton.auth.application.impl.token;

import com.prography.lighton.auth.application.RefreshTokenService;
import com.prography.lighton.auth.application.exception.ExpiredTokenException;
import com.prography.lighton.common.infrastructure.redis.RedisRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisRefreshTokenService implements RefreshTokenService {

    private static final String REFRESH_PREFIX = "refresh:";

    private final RedisRepository redisRepository;

    @Value("${security.jwt.token.refresh.expire-length}")
    private long refreshTokenExpireMillis;

    private String key(String id) {
        return REFRESH_PREFIX + id;
    }

    @Override
    public void saveRefreshToken(String userId, String refreshToken) {
        redisRepository.save(key(userId), refreshToken, Duration.ofMillis(refreshTokenExpireMillis));
    }

    @Override
    public void validateRefreshToken(String userId, String refreshToken) {
        String stored = redisRepository.find(key(userId));
        if (!refreshToken.equals(stored)) {
            throw new ExpiredTokenException();
        }
    }

    @Override
    public void deleteRefreshToken(String userId) {
        redisRepository.delete(key(userId));
    }
}
