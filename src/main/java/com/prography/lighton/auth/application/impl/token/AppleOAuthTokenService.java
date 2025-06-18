package com.prography.lighton.auth.application.impl.token;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prography.lighton.auth.application.exception.IdTokenParseException;
import com.prography.lighton.auth.application.exception.InvalidTokenException;
import com.prography.lighton.auth.infrastructure.AppleKeyUtils;
import com.prography.lighton.auth.infrastructure.config.AppleOAuthProperties;
import com.prography.lighton.auth.presentation.dto.apple.AppleIdTokenPayload;
import com.prography.lighton.auth.presentation.dto.apple.AppleUser;
import java.security.interfaces.ECPrivateKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppleOAuthTokenService {

    private static final String APPLE_AUDIENCE = "https://appleid.apple.com";
    private static final long EXPIRE_DURATION = 60 * 60 * 24 * 180; // 6개월
    private static final int JWT_PARTS_LENGTH = 3;
    private static final int PAYLOAD_INDEX = 1;

    private final AppleOAuthProperties appleOAuthProperties;

    public String createClientSecret() {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(EXPIRE_DURATION);

        ECPrivateKey privateKey = AppleKeyUtils.loadPrivateKey(appleOAuthProperties.getPrivateKeyPath());

        return JWT.create()
                .withKeyId(appleOAuthProperties.getKeyId())
                .withIssuer(appleOAuthProperties.getTeamId())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(exp))
                .withAudience(APPLE_AUDIENCE)
                .withSubject(appleOAuthProperties.getClientId())
                .sign(Algorithm.ECDSA256(null, privateKey));
    }

    public AppleUser parse(String idToken) {
        try {
            String[] parts = idToken.split("\\.");
            if (parts.length != JWT_PARTS_LENGTH) {
                throw new InvalidTokenException("유효하지 않은 Apple ID Token 형식입니다.");
            }

            String payload = parts[PAYLOAD_INDEX];
            byte[] decoded = Base64.getUrlDecoder().decode(payload);
            String json = new String(decoded);

            ObjectMapper objectMapper = new ObjectMapper();
            AppleIdTokenPayload payloadDto = objectMapper.readValue(json, AppleIdTokenPayload.class);

            String email = payloadDto.email();
            return new AppleUser(email);

        } catch (Exception e) {
            throw new IdTokenParseException();
        }
    }
}
