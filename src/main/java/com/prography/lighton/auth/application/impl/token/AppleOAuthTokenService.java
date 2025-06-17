package com.prography.lighton.auth.application.impl.token;


import static com.prography.lighton.common.constant.AuthConstants.EMAIL;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prography.lighton.auth.application.exception.IdTokenParseException;
import com.prography.lighton.auth.application.exception.InvalidTokenException;
import com.prography.lighton.auth.infrastructure.AppleKeyUtils;
import com.prography.lighton.auth.presentation.dto.apple.AppleUser;
import java.security.interfaces.ECPrivateKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppleOAuthTokenService {

    @Value("${apple.oauth.team-id}")
    private String TEAM_ID;
    @Value("${apple.oauth.client-id}")
    private String CLIENT_ID;
    @Value("${apple.oauth.key-id}")
    private String KEY_ID;
    @Value("${apple.oauth.private-key-path}")
    private String KEY_PATH;

    private static final String APPLE_AUDIENCE = "https://appleid.apple.com";
    private static final long EXPIRE_DURATION = 60 * 60 * 24 * 180; // 6개월
    private static final int JWT_PARTS_LENGTH = 3;
    private static final int PAYLOAD_INDEX = 1;

    public String createClientSecret() {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(EXPIRE_DURATION);

        ECPrivateKey privateKey = AppleKeyUtils.loadPrivateKey(KEY_PATH);

        return JWT.create()
                .withKeyId(KEY_ID)
                .withIssuer(TEAM_ID)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(exp))
                .withAudience(APPLE_AUDIENCE)
                .withSubject(CLIENT_ID)
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
            Map<String, Object> payloadMap = objectMapper.readValue(json, new TypeReference<>() {
            });

            String email = (String) payloadMap.get(EMAIL);
            return new AppleUser(email);

        } catch (Exception e) {
            throw new IdTokenParseException();
        }
    }
}
