package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.application.mapper.PerformanceDetailMapper;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.PerformanceRequest;
import com.prography.lighton.performance.common.domain.exception.DuplicatePerformanceRequestException;
import com.prography.lighton.performance.common.presentation.dto.response.GetPerformanceDetailResponseDTO;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRepository;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRequestRepository;
import com.prography.lighton.performance.users.presentation.dto.response.GetMyPerformanceStatsResponseDTO;
import com.prography.lighton.performance.users.presentation.dto.response.GetMyRegisteredPerformanceListResponseDTO;
import com.prography.lighton.performance.users.presentation.dto.response.GetMyRequestedPerformanceListResponseDTO;
import com.prography.lighton.performance.users.presentation.dto.response.GetPerformancePaymentInfoResponse;
import com.prography.lighton.region.domain.entity.SubRegion;
import com.prography.lighton.region.infrastructure.cache.RegionCache;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserPerformanceService {

    private static final String BLANK = " ";

    private final RegionCache regionCache;

    private final PerformanceRepository performanceRepository;
    private final PerformanceRequestRepository performanceRequestRepository;
    private final PerformanceDetailMapper performanceDetailMapper;

    public GetPerformanceDetailResponseDTO getPerformanceDetail(Long performanceId) {
        Performance performance = performanceRepository.getById(performanceId);
        return performanceDetailMapper.toDetailDTO(performance);
    }

    public GetPerformancePaymentInfoResponse getPerformancePaymentDetail(Long performanceId, Integer applySeats) {
        Performance performance = performanceRepository.getById(performanceId);

        return GetPerformancePaymentInfoResponse.of(performance, applySeats);
    }

    @Transactional
    public GetPerformancePaymentInfoResponse requestForPerformance(Long performanceId, Integer applySeats,
                                                                   Member member) {
        Performance performance = performanceRepository.getByIdWithPessimisticLock(performanceId);
        performance.validateApproved();

        if (performanceRequestRepository.existsByMemberAndPerformance(member, performance)) {
            throw new DuplicatePerformanceRequestException();
        }

        PerformanceRequest performanceRequest = performance.createRequest(applySeats, member);
        performanceRequestRepository.save(performanceRequest);

        return GetPerformancePaymentInfoResponse.of(performance, applySeats);
    }

    @Transactional
    public void cancelPerformanceRequest(Long performanceId, Member member) {
        Performance performance = performanceRepository.getByIdWithPessimisticLock(performanceId);
        performance.validateApproved();

        PerformanceRequest performanceRequest = performanceRequestRepository.getByMemberAndPerformance(member,
                performance);

        performance.cancelRequest(performanceRequest.getRequestedSeats());
        performanceRequestRepository.delete(performanceRequest);
    }

    @Transactional
    public void inactivateAllByMember(Member member) {
        List<Performance> performances = performanceRepository.findAllByPerformer(member);
        performances.forEach(Performance::inactivate);

        performanceRequestRepository.bulkInactivateByMember(member);
        performanceRequestRepository.bulkInactivateByPerformances(performances);
    }

    public GetMyRegisteredPerformanceListResponseDTO getMyRegisteredPerformanceList(Member member) {
        return GetMyRegisteredPerformanceListResponseDTO.from(
                performanceRepository.getMyRegisteredOrParticipatedPerformanceList(member));
    }

    public GetMyRequestedPerformanceListResponseDTO getMyRequestedPerformanceList(Member member) {
        return GetMyRequestedPerformanceListResponseDTO.from(
                performanceRequestRepository.getMyRequestedPerformanceList(member)
        );
    }

    public GetMyPerformanceStatsResponseDTO getMyPerformanceStats(Member member) {
        Integer mostParticipatedSubRegionCode = performanceRequestRepository
                .findTopSubRegionId(member.getId());

        if (mostParticipatedSubRegionCode == null) {
            return GetMyPerformanceStatsResponseDTO.of(0, null);
        }

        SubRegion mostParticipatedSubRegion = regionCache.getRegionInfoByCode(mostParticipatedSubRegionCode)
                .getSubRegion();

        return GetMyPerformanceStatsResponseDTO.of(
                performanceRequestRepository.countMyPerformanceApply(member, LocalDate.now(), LocalTime.now()),
                mostParticipatedSubRegion.getRegion().getName()
                        + BLANK
                        + mostParticipatedSubRegion.getName()
        );
    }

    public List<Long> getAppliedPerformances(Member member) {
        return performanceRequestRepository.findAllByMember(member).stream()
                .map(PerformanceRequest::getPerformance)
                .map(Performance::getId)
                .toList();
    }
}
