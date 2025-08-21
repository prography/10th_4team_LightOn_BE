package com.prography.lighton.performance.users.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.application.mapper.PerformanceDetailMapper;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRepository;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRequestRepository;
import com.prography.lighton.performance.users.presentation.dto.response.CheckIsAppliedPerformanceResponse;
import com.prography.lighton.region.infrastructure.cache.RegionCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserPerformanceServiceTest {

    @Mock
    PerformanceRepository performanceRepository;
    @Mock
    PerformanceRequestRepository performanceRequestRepository;
    @Mock
    PerformanceDetailMapper performanceDetailMapper;
    @Mock
    RegionCache regionCache;

    UserPerformanceService userPerformanceService;

    @BeforeEach
    void setUp() {
        userPerformanceService = new UserPerformanceService(regionCache, performanceRepository,
                performanceRequestRepository, performanceDetailMapper);
    }

    @DisplayName("공연 신청 내역 조회 시, 공연 신청 내역이 없으면 false를 반환한다.")
    @Test
    void should_return_false_when_no_performance_application() {
        // Given
        Long performanceId = 1L;
        Member member = mock(Member.class);

        when(performanceRepository.getById(performanceId)).thenReturn(mock(Performance.class));
        when(performanceRequestRepository.existsByMemberAndPerformanceAndRequestStatusNot(
                any(), any(), any())).thenReturn(false);

        // When
        CheckIsAppliedPerformanceResponse appliedForPerformance = userPerformanceService.isAppliedForPerformance(
                performanceId, member);

        // Then
        assertThat(appliedForPerformance).isNotNull();
        assertThat(appliedForPerformance.isApplied()).isFalse();
    }

    @DisplayName("공연 신청 내역 조회 시, 공연 신청 내역이 있으면 true를 반환한다.")
    @Test
    void should_return_true_when_performance_application_exists() {
        // Given
        Long performanceId = 1L;
        Member member = mock(Member.class);

        when(performanceRepository.getById(performanceId)).thenReturn(mock(Performance.class));
        when(performanceRequestRepository.existsByMemberAndPerformanceAndRequestStatusNot(
                any(), any(), any())).thenReturn(true);

        // When
        CheckIsAppliedPerformanceResponse appliedForPerformance = userPerformanceService.isAppliedForPerformance(
                performanceId, member);

        // Then
        assertThat(appliedForPerformance).isNotNull();
        assertThat(appliedForPerformance.isApplied()).isTrue();
    }
}