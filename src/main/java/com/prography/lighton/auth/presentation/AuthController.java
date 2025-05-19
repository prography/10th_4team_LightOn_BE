package com.prography.lighton.auth.presentation;

import com.prography.lighton.auth.application.OAuthUseCase;
import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.presentation.dto.response.LoginMemberResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public void socialLoginRedirect(
            @PathVariable(name = "socialLoginType") String socialLoginPath,
            HttpServletResponse response) throws IOException {
        SocialLoginType socialLoginType = SocialLoginType.valueOf(socialLoginPath.toUpperCase());
        String redirectUrl = oAuthUseCase.accessRequest(socialLoginType);
        response.sendRedirect(redirectUrl);
    }


    @GetMapping(value = "/oauth/{socialLoginType}/callback")
    public ResponseEntity<ApiResult<LoginMemberResponseDTO>> socialLoginCallback(
            @PathVariable(name = "socialLoginType") String socialLoginPath,
            @RequestParam(name = "code") String code) throws RuntimeException {
        SocialLoginType socialLoginType = SocialLoginType.valueOf(socialLoginPath.toUpperCase());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(oAuthUseCase.oAuthLoginOrJoin(socialLoginType, code)));
    }
}