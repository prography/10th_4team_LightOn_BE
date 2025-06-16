package com.prography.lighton.common.presentation;

import com.prography.lighton.auth.application.AuthService;
import com.prography.lighton.auth.application.AuthVerificationService;
import com.prography.lighton.auth.application.LoginMemberUseCase;
import com.prography.lighton.auth.application.OAuthUseCase;
import com.prography.lighton.auth.application.TokenProvider;
import com.prography.lighton.auth.presentation.AuthController;
import com.prography.lighton.genre.infrastructure.cache.GenreCache;
import com.prography.lighton.member.application.CompleteMemberProfileUseCase;
import com.prography.lighton.member.application.ManagePreferredGenreUseCase;
import com.prography.lighton.member.application.RegisterMemberUseCase;
import com.prography.lighton.member.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.infrastructure.repository.PreferredGenreRepository;
import com.prography.lighton.member.presentation.MemberController;
import com.prography.lighton.region.infrastructure.cache.RegionCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WebMvcTest(controllers = {MemberController.class, AuthController.class})
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected RegisterMemberUseCase registerMemberUseCase;

    @MockBean
    protected CompleteMemberProfileUseCase completeMemberProfileUseCase;

    @MockBean
    protected LoginMemberUseCase loginMemberUseCase;

    @MockBean
    protected OAuthUseCase oAuthUseCase;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected ManagePreferredGenreUseCase managePreferredGenreUseCase;

    @MockBean
    protected TokenProvider tokenProvider;

    @MockBean
    protected MemberRepository memberRepository;

    @MockBean
    protected PreferredGenreRepository preferredGenreRepository;

    @MockBean
    protected GenreCache genreCache;

    @MockBean
    protected RegionCache regionCache;

    @MockBean
    protected AuthVerificationService authVerificationService;

}
