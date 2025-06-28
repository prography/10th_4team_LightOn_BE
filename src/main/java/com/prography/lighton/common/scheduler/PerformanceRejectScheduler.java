package com.prography.lighton.common.scheduler;

import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRequestRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PerformanceRejectScheduler {

    private static final Integer REJECT_THRESHOLD_DAYS = 3;
    private final PerformanceRequestRepository performanceRequestRepository;

    // 매일 0시 5분에 실행
    @Scheduled(cron = "* 0 0 * * *", zone = "Asia/Seoul")
    @Transactional
    public void run() {
        performanceRequestRepository.rejectExpiredRequests(LocalDateTime.now().minusDays(REJECT_THRESHOLD_DAYS));
    }
}
