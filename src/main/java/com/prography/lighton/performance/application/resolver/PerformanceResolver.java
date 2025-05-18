package com.prography.lighton.performance.application.resolver;

import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.genre.application.service.GenreService;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.performance.domain.entity.Performance;
import com.prography.lighton.performance.domain.entity.enums.Seat;
import com.prography.lighton.performance.domain.entity.enums.Type;
import com.prography.lighton.performance.domain.entity.vo.Info;
import com.prography.lighton.performance.domain.entity.vo.Location;
import com.prography.lighton.performance.domain.entity.vo.Payment;
import com.prography.lighton.performance.domain.entity.vo.Schedule;
import com.prography.lighton.performance.presentation.dto.PerformanceRegisterRequest;
import com.prography.lighton.region.domain.resolver.RegionResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PerformanceResolver {

    private final GenreService genreService;
    private final RegionResolver regionResolver;

    public Performance resolve(Artist artist, PerformanceRegisterRequest req) {
        Info info = toInfo(req);
        Schedule schedule = toSchedule(req);
        Location location = toLocation(req);
        Payment payment = toPayment(req);
        List<Genre> genres = genreService.getGenresOrThrow(req.info().genre());
        List<Seat> seats = req.seat();

        return Performance.create(
                artist,
                info,
                schedule,
                location,
                payment,
                Type.NORMAL,
                seats,
                genres,
                req.proof()
        );
    }

    private Info toInfo(PerformanceRegisterRequest req) {
        var i = req.info();
        return Info.of(
                i.title(),
                i.description(),
                i.place(),
                i.notice(),
                i.poster()
        );
    }

    private Schedule toSchedule(PerformanceRegisterRequest req) {
        var s = req.schedule();
        return Schedule.of(
                s.startDate(),
                s.endDate(),
                s.startTime(),
                s.endTime()
        );
    }

    private Location toLocation(PerformanceRegisterRequest req) {
        // 위도&경도는 외부 api 사용, 나중에 수정 필요
        Double latitude = 0.0;
        Double longitude = 0.0;

        return Location.of(
                latitude,
                longitude,
                regionResolver.resolve(req.info().location())
        );
    }

    private Payment toPayment(PerformanceRegisterRequest req) {
        var p = req.payment();
        return Payment.of(
                p.isPaid(),
                p.account(),
                p.bank(),
                p.accountHolder(),
                p.price()
        );
    }
}
