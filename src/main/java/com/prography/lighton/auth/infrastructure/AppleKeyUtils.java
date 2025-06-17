package com.prography.lighton.auth.infrastructure;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class AppleKeyUtils {

    private static final String PRIVATE_KEY_BEGIN = "-----BEGIN PRIVATE KEY-----";
    private static final String PRIVATE_KEY_END = "-----END PRIVATE KEY-----";
    private static final String EMPTY = "";
    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String KEY_ALGORITHM = "EC";

    public static ECPrivateKey loadPrivateKey(String classpathLocation) {
        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(classpathLocation)) {

            if (is == null) {
                throw new IllegalArgumentException("리소스를 찾을 수 없습니다: " + classpathLocation);
            }

            String key = new String(is.readAllBytes())
                    .replace(PRIVATE_KEY_BEGIN, EMPTY)
                    .replace(PRIVATE_KEY_END, EMPTY)
                    .replaceAll(WHITESPACE_REGEX, EMPTY);

            byte[] decoded = Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
            return (ECPrivateKey) kf.generatePrivate(spec);

        } catch (Exception e) {
            throw new RuntimeException("Apple private key 로딩 실패", e);
        }
    }
}
