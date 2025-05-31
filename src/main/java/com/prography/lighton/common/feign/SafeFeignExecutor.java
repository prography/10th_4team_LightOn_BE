package com.prography.lighton.common.feign;

import com.prography.lighton.auth.application.exception.InvalidTokenException;
import feign.FeignException;
import java.util.function.Supplier;

public final class SafeFeignExecutor {

    private SafeFeignExecutor() {
    }

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