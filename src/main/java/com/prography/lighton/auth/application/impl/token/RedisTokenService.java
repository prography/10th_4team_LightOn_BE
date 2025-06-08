package com.prography.lighton.auth.application.impl.token;

import com.prography.lighton.auth.application.exception.ExpiredTokenException;
import com.prography.lighton.common.infrastructure.redis.RedisRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisTokenService {

    private static final String REFRESH_PREFIX = "refresh:";
    private static final String BLACKLIST_PREFIX = "blacklist:";

    private final RedisRepository redisRepository;

    @Value("${security.jwt.token.refresh.expire-length}")
    private long refreshTokenExpireMillis;
    @Value("${security.jwt.token.access.expire-length}")
    private long accessTokenExpireMillis;

    public void saveRefreshToken(String id, String refreshToken) {
        redisRepository.save(buildRefreshKey(id), refreshToken, Duration.ofMillis(refreshTokenExpireMillis));
    }

    public void validateRefreshToken(String id, String refreshToken) {
        String existedToken = redisRepository.find(buildRefreshKey(id));
        if (!refreshToken.equals(existedToken)) {
            throw new ExpiredTokenException();
        }
    }

    public void deleteRefreshToken(String id) {
        redisRepository.delete(buildRefreshKey(id));
    }

    private String buildRefreshKey(String value) {
        return REFRESH_PREFIX + value;
    }

    private String buildBlacklistKey(String accessToken) {
        return BLACKLIST_PREFIX + accessToken;
    }
}
