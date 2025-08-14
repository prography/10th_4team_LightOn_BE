package com.prography.lighton.member.users.application.command;

import static com.prography.lighton.common.fixture.MemberTestFixture.TEST_NAME;
import static com.prography.lighton.common.fixture.MemberTestFixture.TEST_PHONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prography.lighton.auth.application.fake.FakeAuthVerificationService;
import com.prography.lighton.auth.application.fake.FakeTokenProvider;
import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.common.domain.vo.RegionInfo;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.domain.entity.TemporaryMember;
import com.prography.lighton.member.common.domain.entity.enums.Authority;
import com.prography.lighton.member.common.domain.entity.vo.Email;
import com.prography.lighton.member.common.domain.entity.vo.Password;
import com.prography.lighton.member.common.domain.exception.DuplicateMemberException;
import com.prography.lighton.member.common.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.common.infrastructure.repository.TemporaryMemberRepository;
import com.prography.lighton.member.users.presentation.dto.request.CompleteMemberProfileRequest;
import com.prography.lighton.member.users.presentation.dto.request.CompleteMemberProfileRequest.AgreementsDTO;
import com.prography.lighton.member.users.presentation.dto.request.CompleteMemberProfileRequest.AgreementsDTO.MarketingDTO;
import com.prography.lighton.member.users.presentation.dto.response.CompleteMemberProfileResponse;
import com.prography.lighton.region.infrastructure.cache.RegionCache;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CompleteProfileServiceTest {

    @Mock
    TemporaryMemberRepository temporaryMemberRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    RegionCache regionCache;

    FakeAuthVerificationService auth = new FakeAuthVerificationService();
    FakeTokenProvider tokens = new FakeTokenProvider();

    @Test
    @DisplayName("회원 프로필을 정상적으로 완료할 수 있다")
    void should_complete_member_profile() {
        // Given
        var svc = new CompleteProfileService(temporaryMemberRepository, memberRepository, auth, tokens, regionCache);

        Long tmpId = 1L;
        int regionCode = 110;

        // 요청 DTO (필드명에 맞춰 수정)
        var req = dummyRequest();

        auth.saveVerifiedStatus(TEST_PHONE.getValue());

        TemporaryMember tmp = mock(TemporaryMember.class);
        when(tmp.getEmail()).thenReturn(mock(Email.class));
        when(tmp.getPassword()).thenReturn(mock(Password.class));
        when(tmp.getLoginType()).thenReturn(SocialLoginType.DEFAULT);
        when(temporaryMemberRepository.getById(tmpId)).thenReturn(tmp);

        // 지역 캐시
        when(regionCache.getRegionInfoByCode(regionCode))
                .thenReturn(mock(RegionInfo.class)); // 생성자/팩토리 맞춰 수정

        // 전화 중복 없음
        when(memberRepository.existsByPhone(any())).thenReturn(false);

        // 저장 결과 Member
        Member saved = mock(Member.class);
        when(saved.getId()).thenReturn(42L);
        when(saved.getAuthority()).thenReturn(Authority.NORMAL);
        when(memberRepository.save(any(Member.class))).thenReturn(saved);

        // When
        CompleteMemberProfileResponse res = svc.handle(tmpId, req);

        // Then
        verify(temporaryMemberRepository).getById(tmpId);
        verify(memberRepository).existsByPhone(any());
        verify(memberRepository).save(any(Member.class));
        verify(regionCache).getRegionInfoByCode(regionCode);

        assertThat(res).isNotNull();
        assertThat(res.accessToken()).isNotBlank();
        assertThat(res.refreshToken()).isNotBlank();
        verify(tmp).markAsRegistered();
    }

    @Test
    @DisplayName("전화번호가 중복이면 예외가 발생한다.")
    void should_throw_when_phone_duplicate() {
        // Given
        var svc = new CompleteProfileService(temporaryMemberRepository, memberRepository, auth, tokens, regionCache);

        Long tmpId = 1L;
        var req = dummyRequest();
        auth.saveVerifiedStatus(TEST_PHONE.getValue());

        TemporaryMember tmp = mock(TemporaryMember.class);
        when(tmp.isRegistered()).thenReturn(false);

        when(temporaryMemberRepository.getById(tmpId)).thenReturn(tmp);
        when(regionCache.getRegionInfoByCode(anyInt())).thenReturn(mock(RegionInfo.class));
        when(memberRepository.existsByPhone(any())).thenReturn(true);

        // When & Then
        assertThrows(DuplicateMemberException.class, () -> svc.handle(tmpId, req));
        verify(memberRepository, never()).save(any());
    }

    @Test
    @DisplayName("이미 등록된 임시회원이면 예외가 발생한다.")
    void should_throw_when_already_registered() {
        // Given
        var svc = new CompleteProfileService(temporaryMemberRepository, memberRepository, auth, tokens, regionCache);

        Long tmpId = 1L;
        var req = dummyRequest();
        auth.saveVerifiedStatus(TEST_PHONE.getValue());

        TemporaryMember tmp = mock(TemporaryMember.class);
        when(tmp.isRegistered()).thenReturn(true); // <- 이미 등록
        when(temporaryMemberRepository.getById(tmpId)).thenReturn(tmp);

        // When & Then
        assertThrows(DuplicateMemberException.class, () -> svc.handle(tmpId, req));
        verify(memberRepository, never()).save(any());
    }

    // ---- 헬퍼 ----
    private CompleteMemberProfileRequest dummyRequest() {
        return new CompleteMemberProfileRequest(
                TEST_NAME,
                TEST_PHONE.getValue(),
                110,
                new AgreementsDTO(
                        true, true, true,
                        new MarketingDTO(true, true, true)
                )
        );
    }
}
