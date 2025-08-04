package com.prography.lighton.auth.application.fake;

import com.prography.lighton.auth.application.port.SmsSender;
import java.util.ArrayList;
import java.util.List;

public class FakeSmsSender implements SmsSender {

    private final List<SentSms> sentMessages = new ArrayList<>();

    @Override
    public void sendSms(String to, String message) {
        sentMessages.add(new SentSms(to, message));
    }

    public List<SentSms> getSentMessages() {
        return sentMessages;
    }

    public boolean wasSmsSentTo(String phoneNumber) {
        return sentMessages.stream()
                .anyMatch(sms -> sms.to.equals(phoneNumber));
    }

    public record SentSms(String to, String message) {
    }
}