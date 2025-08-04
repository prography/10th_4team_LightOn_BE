package com.prography.lighton.auth.application.service;

import com.prography.lighton.auth.application.exception.PhoneVerificationFailedException;
import com.prography.lighton.auth.application.port.AuthVerificationService;
import com.prography.lighton.auth.domain.vo.VerificationCode;
import com.prography.lighton.auth.infrastructure.sms.SmsService;
import com.prography.lighton.auth.presentation.dto.request.VerifyPhoneRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PhoneVerificationService {

    private final SmsService smsService;
    private final AuthVerificationService authVerificationService;

    public void sendAuthCode(String phoneNumber) {
        VerificationCode code = VerificationCode.generateCode();
        smsService.sendSms(phoneNumber, code.getValue());
        authVerificationService.saveCode(phoneNumber, code.getValue());
    }


    public void verifyPhone(VerifyPhoneRequestDTO request) {
        if (!authVerificationService.isCodeMatched(request.phoneNumber(), request.code())) {
            throw new PhoneVerificationFailedException();
        }
        authVerificationService.saveVerifiedStatus(request.phoneNumber());
    }
}
