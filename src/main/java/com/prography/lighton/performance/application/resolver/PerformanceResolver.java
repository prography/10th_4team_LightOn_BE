package com.prography.lighton.performance.application.resolver;

import com.prography.lighton.genre.application.service.GenreService;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.performance.domain.entity.enums.Seat;
import com.prography.lighton.performance.domain.entity.vo.Info;
import com.prography.lighton.performance.domain.entity.vo.Location;
import com.prography.lighton.performance.domain.entity.vo.Payment;
import com.prography.lighton.performance.domain.entity.vo.Schedule;
import com.prography.lighton.performance.presentation.dto.InfoDTO;
import com.prography.lighton.performance.presentation.dto.PaymentDTO;
import com.prography.lighton.performance.presentation.dto.ScheduleDTO;
import com.prography.lighton.region.infrastructure.cache.RegionCache;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PerformanceResolver {

    private final GenreService genreService;
    private final RegionCache regionCache;

    public DomainData toDomainData(InfoDTO infoDTO, ScheduleDTO scheduleDTO, PaymentDTO paymentDTO, List<Seat> seats) {
        return new DomainData(
                toInfo(infoDTO),
                toSchedule(scheduleDTO),
                toLocation(infoDTO),
                toPayment(paymentDTO),
                seats,
                genreService.getGenresOrThrow(infoDTO.genre())
        );
    }

    private Info toInfo(InfoDTO req) {
        return Info.of(
                req.title(),
                req.description(),
                req.place(),
                req.notice(),
                req.poster()
        );
    }

    private Schedule toSchedule(ScheduleDTO req) {
        return Schedule.of(
                req.startDate(),
                req.endDate(),
                req.startTime(),
                req.endTime()
        );
    }

    private Location toLocation(InfoDTO req) {
        // 위도&경도는 외부 api 사용, 나중에 수정 필요
        Double latitude = 0.0;
        Double longitude = 0.0;

        return Location.of(
                latitude,
                longitude,
                regionCache.getRegionInfoByCode(req.location())
        );
    }

    private Payment toPayment(PaymentDTO req) {
        return Payment.of(
                req.isPaid(),
                req.account(),
                req.bank(),
                req.accountHolder(),
                req.price()
        );
    }

    public record DomainData(
            Info info,
            Schedule schedule,
            Location location,
            Payment payment,
            List<Seat> seats,
            List<Genre> genres
    ) {
    }
}
