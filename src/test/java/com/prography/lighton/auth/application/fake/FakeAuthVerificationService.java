package com.prography.lighton.auth.application.fake;

import com.prography.lighton.auth.application.exception.PhoneNotVerifiedException;
import com.prography.lighton.auth.application.port.AuthVerificationService;
import java.util.HashMap;
import java.util.Map;

public class FakeAuthVerificationService implements AuthVerificationService {

    private final Map<String, String> codeStorage = new HashMap<>();
    private final Map<String, Boolean> verifiedStatus = new HashMap<>();

    @Override
    public void saveCode(String phoneNumber, String code) {
        codeStorage.put(phoneNumber, code);
    }

    @Override
    public boolean isCodeMatched(String phoneNumber, String inputCode) {
        return inputCode != null && inputCode.equals(codeStorage.get(phoneNumber));
    }

    @Override
    public void saveVerifiedStatus(String phoneNumber) {
        verifiedStatus.put(phoneNumber, true);
    }

    @Override
    public void checkIsVerified(String phoneNumber) {
        if (!Boolean.TRUE.equals(verifiedStatus.get(phoneNumber))) {
            throw new PhoneNotVerifiedException();
        }
    }
}
