package com.prography.lighton.member.presentation;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.prography.lighton.common.presentation.ControllerTest;
import com.prography.lighton.member.presentation.dto.response.LoginMemberResponseDTO;
import com.prography.lighton.member.presentation.dto.response.RegisterMemberResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

@AutoConfigureMockMvc
class MemberControllerTest extends ControllerTest {

    @Test
    @WithMockUser
    void register() throws Exception {
        // given
        given(registerMemberUseCase.registerMember(any()))
                .willReturn(RegisterMemberResponseDTO.of(123L));

        // when & then
        mockMvc.perform(post("/api/members")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content("""
                                {
                                    "email": "test@example.com",
                                    "password": "pass1234!"
                                }
                                """))
                .andExpect(status().isOk())
                .andDo(document("register-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("회원가입 API")
                                .summary("회원가입 API")
                                .description("""
                                        ## 회원가입 API입니다. 
                                        - 이메일, 비밀번호를 입력 받아 회원가입을 진행합니다.
                                        - 해당 API를 호출하면 임시 회원 ID가 발급됩니다. 
                                        - 해당 임시 회원 ID는 회원가입 후 프로필을 완성하는 데 사용됩니다. 
                                        - 프로필을 완성해야 정상적으로 서비스를 이용할 수 있습니다.
                                        """)
                                .requestFields(
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("password").description("비밀번호")
                                )
                                .responseFields(
                                        fieldWithPath("success").description("응답 성공 여부"),
                                        fieldWithPath("response.temporaryUserId").description("임시 회원 ID"),
                                        fieldWithPath("error").description("에러 정보 (없으면 null)")
                                )
                                .build()
                        )
                ));
    }

    @Test
    @WithMockUser
    void login() throws Exception {
        // given
        given(loginMemberUseCase.login(any()))
                .willReturn(LoginMemberResponseDTO.of("aaa.ccc.ddd", "refreshToken"));

        // when & then: 로그인 API 호출 및 문서화
        mockMvc.perform(post("/api/members/login")
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content("""
                                    {
                                        "email": "test@example.com",
                                        "password": "pass1234!"
                                    }
                                """))
                .andExpect(status().isOk())
                .andDo(document("login-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("로그인 API")
                                .summary("로그인 API")
                                .description("""
                                            ## 로그인 API입니다.
                                            - 이메일, 비밀번호를 입력 받아 로그인을 진행합니다.
                                            - 해당 API를 호출하면 accessToken과 refreshToken이 발급됩니다.
                                        """)
                                .requestFields(
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("password").description("비밀번호")
                                )
                                .responseFields(
                                        fieldWithPath("success").description("응답 성공 여부"),
                                        fieldWithPath("response.accessToken").description("엑세스 토큰"),
                                        fieldWithPath("response.refreshToken").description("리프레시 토큰"),
                                        fieldWithPath("error").description("에러 정보 (없으면 null)")
                                )
                                .build()
                        )
                ));
    }
}
