package com.prography.lighton.auth.application.impl;

import com.prography.lighton.auth.application.AuthVerificationService;
import com.prography.lighton.auth.application.exception.PhoneNotVerifiedException;
import com.prography.lighton.common.infrastructure.redis.RedisRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisAuthVerificationService implements AuthVerificationService {

    private static final String CODE_PREFIX = "auth:code:";
    private static final String STATUS_PREFIX = "auth:status:";
    private static final String VERIFIED_STATUS = "VERIFIED";

    private static final Duration CODE_TTL = Duration.ofMinutes(5);
    private static final Duration STATUS_TTL = Duration.ofMinutes(10);

    private final RedisRepository redisRepository;

    @Override
    public void saveCode(String phoneNumber, String code) {
        redisRepository.save(buildCodeKey(phoneNumber), code, CODE_TTL);
    }

    @Override
    public boolean isCodeMatched(String phoneNumber, String inputCode) {
        String savedCode = redisRepository.find(buildCodeKey(phoneNumber));
        return inputCode != null && inputCode.equals(savedCode);
    }

    @Override
    public void saveVerifiedStatus(String phoneNumber) {
        redisRepository.save(buildStatusKey(phoneNumber), VERIFIED_STATUS, STATUS_TTL);
    }

    @Override
    public void checkIsVerified(String phoneNumber) {
        if (!isVerified(phoneNumber)) {
            throw new PhoneNotVerifiedException();
        }
    }

    private boolean isVerified(String phoneNumber) {
        return VERIFIED_STATUS.equals(redisRepository.find(buildStatusKey(phoneNumber)));
    }

    private String buildCodeKey(String phoneNumber) {
        return CODE_PREFIX + phoneNumber;
    }

    private String buildStatusKey(String phoneNumber) {
        return STATUS_PREFIX + phoneNumber;
    }
}
