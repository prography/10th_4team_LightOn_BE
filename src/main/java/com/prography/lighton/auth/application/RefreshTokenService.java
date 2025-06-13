package com.prography.lighton.auth.application;

public interface RefreshTokenService {
    void saveRefreshToken(String userId, String refreshToken);

    void validateRefreshToken(String userId, String refreshToken);

    void deleteRefreshToken(String userId);
}
