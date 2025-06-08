package com.prography.lighton.auth.application.impl;

import com.prography.lighton.auth.application.AuthService;
import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.auth.application.dto.TokenDTO;
import com.prography.lighton.auth.presentation.dto.response.ReissueTokenResponse;
import com.prography.lighton.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenProvider tokenProvider;

    @Override
    public ReissueTokenResponse reissueLoginTokens(String refreshToken) {
        TokenDTO tokenDTO = tokenProvider.reissueTokens(refreshToken);

        return ReissueTokenResponse.of(
                tokenDTO.accessToken(),
                tokenDTO.refreshToken()
        );
    }

    @Override
    public void logout(Member member) {
        tokenProvider.expireTokens(member.getId());
    }
}
