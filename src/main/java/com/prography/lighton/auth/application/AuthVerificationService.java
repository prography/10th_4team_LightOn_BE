package com.prography.lighton.auth.application;

public interface AuthVerificationService {

    void saveCode(String phoneNumber, String code);

    boolean isCodeMatched(String phoneNumber, String inputCode);

    void saveVerifiedStatus(String phoneNumber);

    boolean isVerified(String phoneNumber);
}