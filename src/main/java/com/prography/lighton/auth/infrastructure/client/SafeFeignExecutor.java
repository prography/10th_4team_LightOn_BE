package com.prography.lighton.auth.infrastructure.client;

import com.prography.lighton.auth.application.exception.ExternalApiCallException;
import com.prography.lighton.auth.application.exception.InvalidTokenException;
import feign.FeignException;

public class SafeFeignExecutor {

    @FunctionalInterface
    public interface FeignApiCall<T> {
        T call() throws FeignException;
    }

    public static <T> T execute(FeignApiCall<T> call, String failMessage) {
        try {
            return call.call();
        } catch (FeignException e) {
            int status = e.status();
            if (status == 401 || status == 403) {
                throw new InvalidTokenException("유효하지 않은 소셜 토큰입니다.");
            }
            throw new ExternalApiCallException();
        }
    }

}
