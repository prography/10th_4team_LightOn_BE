package com.prography.lighton.auth.application;

public interface RefreshTokenService {
    void save(String userId, String refreshToken);

    void validate(String userId, String refreshToken);

    void delete(String userId);
}
