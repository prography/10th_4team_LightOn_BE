package com.prography.lighton.auth.application;

import com.prography.lighton.auth.presentation.dto.response.ReissueTokenResponse;

public interface AuthService {

    ReissueTokenResponse reissueLoginTokens(String refreshToken);
}
