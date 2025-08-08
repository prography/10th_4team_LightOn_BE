package com.prography.lighton.auth.application.port;

public interface AuthVerificationService {

    void saveCode(String phoneNumber, String code);

    boolean isCodeMatched(String phoneNumber, String inputCode);

    void saveVerifiedStatus(String phoneNumber);

    void checkIsVerified(String phoneNumber);
}
