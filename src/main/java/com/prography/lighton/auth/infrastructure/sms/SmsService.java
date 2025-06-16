package com.prography.lighton.auth.infrastructure.sms;

import com.prography.lighton.auth.infrastructure.sms.exception.SmsSendFailedException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SmsService {

    private static final String AUTH_MESSAGE_TEMPLATE = "[Light ON] 인증번호는 %s 입니다. 정확히 입력해주세요.";

    private final SmsProperties smsProperties;

    private DefaultMessageService messageService;

    @PostConstruct
    public void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(
                smsProperties.apiKey(),
                smsProperties.apiSecret(),
                smsProperties.apiUrl()
        );
    }

    public void sendSms(String to, String text) {
        Message message = createMessage(to, text);

        try {
            messageService.sendOne(new SingleMessageSendingRequest(message));
        } catch (Exception e) {
            log.error("[SMS] 인증번호 전송 실패 ", e);
            throw new SmsSendFailedException();
        }
    }

    private Message createMessage(String to, String text) {
        Message message = new Message();
        message.setFrom(smsProperties.fromNumber());
        message.setTo(to);
        message.setText(buildAuthMessage(text));
        return message;
    }

    private String buildAuthMessage(String value) {
        return String.format(AUTH_MESSAGE_TEMPLATE, value);
    }
}
