package com.prography.lighton.region.application.service;

import com.prography.lighton.auth.application.exception.InvalidTokenException;
import com.prography.lighton.region.infrastructure.api.ExternalApiCallException;
import feign.FeignException;
import java.util.function.Supplier;

public final class SafeFeignExecutor {

    private SafeFeignExecutor() {
    }  // util class

    /**
     * Feign 호출 래핑. - 401, 403 → InvalidTokenException - 기타 → ExternalApiCallException
     */
    public static <T> T run(Supplier<T> call) {
        try {
            return call.get();
        } catch (FeignException e) {
            switch (e.status()) {
                case 401, 403 -> throw new InvalidTokenException();
                default -> throw new ExternalApiCallException();
            }
        }
    }
}