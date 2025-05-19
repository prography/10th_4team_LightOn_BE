package com.prography.lighton.auth.presentation;

import com.prography.lighton.auth.application.OAuthUseCase;
import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.auth.presentation.dto.response.RegisterOAuthMemberResponseDTO;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final OAuthUseCase oAuthUseCase;

    @GetMapping("/oauth/{socialLoginType}")
    public void socialLoginRedirect(@PathVariable(name = "socialLoginType") String SocialLoginPath) throws IOException {
        SocialLoginType socialLoginType = SocialLoginType.valueOf(SocialLoginPath.toUpperCase());
        oAuthUseCase.accessRequest(socialLoginType);
    }

    @GetMapping(value = "/oauth/{socialLoginType}/callback")
    public RegisterOAuthMemberResponseDTO socialLoginCallback(
            @PathVariable(name = "socialLoginType") String socialLoginPath,
            @RequestParam(name = "code") String code) throws IOException, RuntimeException {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code : {}", code);
        SocialLoginType socialLoginType = SocialLoginType.valueOf(socialLoginPath.toUpperCase());
        oAuthUseCase.oAuthLoginOrJoin(socialLoginType, code);
        return null;
    }
}