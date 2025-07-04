package com.prography.lighton.auth.infrastructure;

import com.prography.lighton.auth.application.exception.ApplePrivateKeyLoadException;
import com.prography.lighton.common.exception.base.NotFoundException;
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

    private static volatile ECPrivateKey cachedKey;

    public static ECPrivateKey loadPrivateKey(String classpathLocation) {
        if (cachedKey == null) {
            synchronized (AppleKeyUtils.class) {
                if (cachedKey == null) {
                    cachedKey = readPrivateKeyFromFile(classpathLocation);
                }
            }
        }
        return cachedKey;
    }

    private static ECPrivateKey readPrivateKeyFromFile(String classpathLocation) {
        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(classpathLocation)) {

            if (is == null) {
                throw new NotFoundException("리소스를 찾을 수 없습니다: " + classpathLocation);
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
            throw new ApplePrivateKeyLoadException("Apple .p8 비밀키 로딩 중 오류 발생");
        }
    }
}
