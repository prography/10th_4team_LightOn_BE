package com.prography.lighton.auth.application.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prography.lighton.artist.users.application.service.ArtistService;
import com.prography.lighton.common.fixture.MemberTestFixture;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.infrastructure.repository.MemberRepository;
import com.prography.lighton.member.common.infrastructure.repository.TemporaryMemberRepository;
import com.prography.lighton.member.users.application.command.InactivateMemberService;
import com.prography.lighton.performance.users.application.service.UserPerformanceLikeService;
import com.prography.lighton.performance.users.application.service.UserPerformanceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class MemberWithdrawalServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    TemporaryMemberRepository temporaryMemberRepository;
    @Mock
    UserPerformanceService userPerformanceService;
    @Mock
    UserPerformanceLikeService userPerformanceLikeService;
    @Mock
    InactivateMemberService inactivateMemberService;
    @Mock
    ArtistService artistService;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    MemberWithdrawalService withdrawalService;

    @Test
    @DisplayName("회원 탈퇴를 수행할 수 있다.")
    void should_withdraw_member() {
        // given
        Member member = MemberTestFixture.createNormalMember(passwordEncoder);
        when(memberRepository.getMemberById(member.getId())).thenReturn(member);

        // when
        withdrawalService.withdraw(member);

        // then
        verify(userPerformanceService).inactivateAllByMember(member);
        verify(inactivateMemberService).handle(member);
        verify(userPerformanceLikeService).inactivateAllByMember(member);
        verify(artistService).inactiveByMember(member);
        verify(temporaryMemberRepository).deleteByEmail(member.getEmail());
    }
}
