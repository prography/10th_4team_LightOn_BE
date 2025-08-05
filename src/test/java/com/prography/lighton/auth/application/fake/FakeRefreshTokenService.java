package com.prography.lighton.auth.application.fake;

import com.prography.lighton.auth.application.exception.InvalidTokenException;
import com.prography.lighton.auth.application.port.RefreshTokenService;
import java.util.HashMap;
import java.util.Map;

public class FakeRefreshTokenService implements RefreshTokenService {

    private final Map<String, String> store = new HashMap<>();

    @Override
    public void saveRefreshToken(String userId, String refreshToken) {
        store.put(userId, refreshToken);
    }

    @Override
    public void validateRefreshToken(String userId, String refreshToken) {
        String saved = store.get(userId);
        if (saved == null || !saved.equals(refreshToken)) {
            throw new InvalidTokenException();
        }
    }

    @Override
    public void deleteRefreshToken(String userId) {
        store.remove(userId);
    }

    public String getRefreshToken(String userId) {
        if (!store.containsKey(userId)) {
            return null;
        }
        return store.get(userId);
    }
}
