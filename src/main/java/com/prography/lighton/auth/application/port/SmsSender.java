package com.prography.lighton.auth.application.port;

public interface SmsSender {

    void sendSms(String to, String message);
}
