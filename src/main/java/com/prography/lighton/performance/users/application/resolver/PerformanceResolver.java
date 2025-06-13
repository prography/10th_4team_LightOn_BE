package com.prography.lighton.performance.users.application.resolver;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.artist.users.application.service.ArtistService;
import com.prography.lighton.genre.application.service.GenreService;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.enums.Seat;
import com.prography.lighton.performance.common.domain.entity.vo.Info;
import com.prography.lighton.performance.common.domain.entity.vo.Location;
import com.prography.lighton.performance.common.domain.entity.vo.Payment;
import com.prography.lighton.performance.common.domain.entity.vo.Schedule;
import com.prography.lighton.performance.users.presentation.dto.InfoDTO;
import com.prography.lighton.performance.users.presentation.dto.PaymentDTO;
import com.prography.lighton.performance.users.presentation.dto.ScheduleDTO;
import com.prography.lighton.region.application.dto.Coordinate;
import com.prography.lighton.region.application.service.AddressGeocodingService;
import com.prography.lighton.region.infrastructure.cache.RegionCache;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PerformanceResolver {

    private final GenreService genreService;
    private final ArtistService artistService;
    private final RegionCache regionCache;
    private final AddressGeocodingService addressGeocodingService;

    public DomainData toDomainData(Member member, List<Long> artists, InfoDTO infoDTO, ScheduleDTO scheduleDTO,
                                   PaymentDTO paymentDTO, List<Seat> seats) {
        return new DomainData(
                toMasterArtist(member),
                toArtists(artists),
                toInfo(infoDTO),
                toSchedule(scheduleDTO),
                toLocation(infoDTO),
                toPayment(paymentDTO),
                seats,
                genreService.getGenresOrThrow(infoDTO.genre())
        );
    }

    private Artist toMasterArtist(Member member) {
        return artistService.getApprovedArtistByMember(member);
    }

    private List<Artist> toArtists(List<Long> artistIds) {
        return artistService.getApprovedArtistsByIds(artistIds);
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
        Coordinate coordinate = addressGeocodingService.geocode(req.place());
        Double latitude = coordinate.latitude();
        Double longitude = coordinate.longitude();

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
            Artist master,
            List<Artist> artists,
            Info info,
            Schedule schedule,
            Location location,
            Payment payment,
            List<Seat> seats,
            List<Genre> genres
    ) {
    }
}
