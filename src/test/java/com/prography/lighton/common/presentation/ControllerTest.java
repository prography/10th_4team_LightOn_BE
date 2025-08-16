package com.prography.lighton.common.presentation;

import com.prography.lighton.auth.application.port.AuthVerificationService;
import com.prography.lighton.auth.application.port.TokenProvider;
import com.prography.lighton.auth.application.service.LoginMemberService;
import com.prography.lighton.auth.application.service.MemberWithdrawalService;
import com.prography.lighton.auth.application.service.OAuthService;
import com.prography.lighton.auth.application.service.PhoneVerificationService;
import com.prography.lighton.auth.application.service.TokenReissueService;
import com.prography.lighton.auth.presentation.AuthController;
import com.prography.lighton.genre.infrastructure.cache.GenreCache;
import com.prography.lighton.member.common.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.common.infrastructure.repository.PreferredGenreRepository;
import com.prography.lighton.member.users.application.command.CompleteProfileService;
import com.prography.lighton.member.users.application.command.EditPreferredGenreService;
import com.prography.lighton.member.users.application.command.RegisterMemberService;
import com.prography.lighton.member.users.application.query.CheckEmailExistService;
import com.prography.lighton.member.users.application.query.GetMemberInfoService;
import com.prography.lighton.member.users.application.query.GetPreferredArtistsService;
import com.prography.lighton.member.users.application.query.GetPreferredGenreService;
import com.prography.lighton.member.users.presentation.UserMemberCommandController;
import com.prography.lighton.region.infrastructure.cache.RegionCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WebMvcTest(controllers = {UserMemberCommandController.class, AuthController.class})
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected CompleteProfileService completeProfileService;

    @MockBean
    protected RegisterMemberService registerMemberService;

    @MockBean
    protected EditPreferredGenreService editPreferredGenreService;

    @MockBean
    protected CheckEmailExistService checkEmailExistsService;

    @MockBean
    protected GetMemberInfoService getMemberInfoService;

    @MockBean
    protected GetPreferredArtistsService getPreferredArtistsService;

    @MockBean
    protected GetPreferredGenreService getPreferredGenreService;

    @MockBean
    protected LoginMemberService loginMemberService;

    @MockBean
    protected OAuthService oAuthService;

    @MockBean
    protected TokenReissueService tokenReissueService;

    @MockBean
    protected MemberWithdrawalService memberWithdrawalService;

    @MockBean
    protected PhoneVerificationService phoneVerificationService;

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
