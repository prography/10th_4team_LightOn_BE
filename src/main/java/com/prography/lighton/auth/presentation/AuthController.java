package com.prography.lighton.auth.presentation;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.prography.lighton.auth.application.service.LoginMemberService;
import com.prography.lighton.auth.application.service.MemberWithdrawalService;
import com.prography.lighton.auth.application.service.OAuthService;
import com.prography.lighton.auth.application.service.PhoneVerificationService;
import com.prography.lighton.auth.application.service.TokenReissueService;
import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.auth.presentation.dto.request.SendAuthCodeRequest;
import com.prography.lighton.auth.presentation.dto.request.VerifyPhoneRequest;
import com.prography.lighton.auth.presentation.dto.response.ReissueTokenResponse;
import com.prography.lighton.auth.presentation.dto.response.login.SocialLoginResult;
import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.users.presentation.dto.request.LoginMemberRequest;
import com.prography.lighton.member.users.presentation.dto.response.LoginMemberResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final LoginMemberService loginMemberService;
    private final OAuthService oAuthService;
    private final TokenReissueService tokenReissueService;
    private final MemberWithdrawalService memberWithdrawalService;
    private final PhoneVerificationService phoneVerificationService;

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResult<LoginMemberResponse>> login(@RequestBody @Valid LoginMemberRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(loginMemberService.login(request)));
    }

    @GetMapping("/oauth/{socialLoginType}")
    public void socialLoginRedirect(
            @PathVariable(name = "socialLoginType") String socialLoginPath,
            HttpServletResponse response) throws IOException {
        SocialLoginType socialLoginType = SocialLoginType.from(socialLoginPath.toUpperCase());
        String redirectUrl = oAuthService.accessRequest(socialLoginType);
        response.sendRedirect(redirectUrl);
    }


    @RequestMapping(value = "/oauth/{socialLoginType}/callback", method = {GET, POST})
    public ResponseEntity<ApiResult<SocialLoginResult>> socialLoginCallback(
            @PathVariable(name = "socialLoginType") String socialLoginPath,
            @RequestParam(name = "code") String code) throws RuntimeException {
        SocialLoginType socialLoginType = SocialLoginType.from(socialLoginPath.toUpperCase());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(oAuthService.oAuthLoginOrJoin(socialLoginType, code)));
    }

    @PostMapping("/auth/token/refresh")
    public ResponseEntity<ApiResult<ReissueTokenResponse>> reissueLoginTokens(
            @RequestHeader("Refresh-Token") String refreshToken) {
        ReissueTokenResponse response = tokenReissueService.reissueLoginTokens(refreshToken);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(response));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<ApiResult<String>> logout(@LoginMember Member member) {
        tokenReissueService.logout(member.getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success());
    }

    @DeleteMapping("/auth/me")
    public ResponseEntity<ApiResult<String>> withdraw(@LoginMember Member member) {
        memberWithdrawalService.withdraw(member);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiUtils.success());
    }

    @PostMapping("/auth/phones/code")
    public ResponseEntity<ApiResult<String>> sendAuthCode(@Valid @RequestBody SendAuthCodeRequest request) {
        phoneVerificationService.sendAuthCode(request.phoneNumber());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success());
    }

    @PostMapping("/auth/phones/code/verify")
    public ResponseEntity<ApiResult<String>> verifyPhone(@Valid @RequestBody VerifyPhoneRequest request) {
        phoneVerificationService.verifyPhone(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success());
    }

}
