package com.prography.lighton.auth.domain.vo;

import java.security.SecureRandom;
import lombok.Getter;

@Getter
public class VerificationCode {

    private static final int CODE_LENGTH = 6;
    private static final int CODE_MIN = (int) Math.pow(10, CODE_LENGTH - 1);  // 100000
    private static final int CODE_MAX = (int) Math.pow(10, CODE_LENGTH) - CODE_MIN;  // 900000

    private static final SecureRandom secureRandom = new SecureRandom();

    private final String value;

    public VerificationCode(String value) {
        if (!value.matches("\\d{" + CODE_LENGTH + "}")) {
            throw new IllegalArgumentException(CODE_LENGTH + "자리 숫자만 허용됩니다.");
        }
        this.value = value;
    }

    public static VerificationCode generateCode() {
        int code = secureRandom.nextInt(CODE_MAX) + CODE_MIN;
        return new VerificationCode(String.valueOf(code));
    }
}
