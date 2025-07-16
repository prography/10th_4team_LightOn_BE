package com.prography.lighton.performance.users.application.resolver;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.artist.users.application.service.ArtistService;
import com.prography.lighton.common.application.s3.S3UploadService;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.genre.infrastructure.cache.GenreCache;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.enums.Seat;
import com.prography.lighton.performance.common.domain.entity.vo.Info;
import com.prography.lighton.performance.common.domain.entity.vo.Location;
import com.prography.lighton.performance.common.domain.entity.vo.Payment;
import com.prography.lighton.performance.common.domain.entity.vo.Schedule;
import com.prography.lighton.performance.users.presentation.dto.request.InfoDTO;
import com.prography.lighton.performance.users.presentation.dto.request.PaymentDTO;
import com.prography.lighton.performance.users.presentation.dto.request.RegisterPerformanceMultiPart;
import com.prography.lighton.performance.users.presentation.dto.request.SavePerformanceRequest;
import com.prography.lighton.performance.users.presentation.dto.request.ScheduleDTO;
import com.prography.lighton.performance.users.presentation.dto.request.UpdatePerformanceMultiPart;
import com.prography.lighton.region.application.dto.Coordinate;
import com.prography.lighton.region.application.service.AddressGeocodingService;
import com.prography.lighton.region.infrastructure.cache.RegionCache;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class PerformanceResolver {

    private final GenreCache genreCache;
    private final ArtistService artistService;
    private final RegionCache regionCache;
    private final AddressGeocodingService addressGeocodingService;
    private final S3UploadService uploadService;

    public PerformanceData toNewPerformanceData(Member member, RegisterPerformanceMultiPart request) {
        String posterUrl = uploadService.uploadFile(request.posterImage(), member);
        String proofUrl = uploadService.uploadFile(request.proof(), member);
        SavePerformanceRequest data = request.data();

        return new PerformanceData(
                toArtists(member, data.artists()),
                toInfo(data.info(), posterUrl),
                toSchedule(data.schedule()),
                toLocation(data.info()),
                toPayment(data.payment()),
                data.seat(),
                genreCache.getGenresByNameOrThrow(data.info().genre()),
                proofUrl
        );
    }

    public PerformanceData toUpdatePerformanceData(Member member,
                                                   Performance origin,
                                                   UpdatePerformanceMultiPart request) {

        String posterUrl = replaceSingle(origin.getInfo().getPosterUrl(), request.posterImage(), member);
        String proofUrl = replaceSingle(origin.getProofUrl(), request.proof(), member);
        SavePerformanceRequest data = request.data();

        return new PerformanceData(
                toArtists(member, data.artists()),
                toInfo(data.info(), posterUrl),
                toSchedule(data.schedule()),
                toLocation(data.info()),
                toPayment(data.payment()),
                data.seat(),
                genreCache.getGenresByNameOrThrow(data.info().genre()),
                proofUrl
        );
    }

    public BuskingData toBuskingData(Member member, InfoDTO infoDTO, ScheduleDTO scheduleDTO) {
        return new BuskingData(
                member,
                toInfo(infoDTO, "posterUrl"),
                toSchedule(scheduleDTO),
                toLocation(infoDTO),
                genreCache.getGenresByNameOrThrow(infoDTO.genre()));
    }

    private List<Artist> toArtists(Member member, List<Long> artistIds) {
        Artist artist = artistService.getApprovedArtistByMember(member);
        artistIds.add(artist.getId());
        return artistService.getApprovedArtistsByIds(artistIds);
    }

    private Info toInfo(InfoDTO req, String posterUrl) {
        return Info.of(
                req.title(),
                req.description(),
                req.place(),
                req.notice(),
                posterUrl
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

    private String replaceSingle(String originUrl, MultipartFile file, Member member) {
        if (file != null && !file.isEmpty()) {
            uploadService.deleteFile(originUrl);
            return uploadService.uploadFile(file, member);
        }
        return originUrl;
    }


    public record PerformanceData(
            List<Artist> artists,
            Info info,
            Schedule schedule,
            Location location,
            Payment payment,
            List<Seat> seats,
            List<Genre> genres,
            String proofUrl
    ) {
    }

    public record BuskingData(
            Member performer,
            Info info,
            Schedule schedule,
            Location location,
            List<Genre> genres
    ) {
    }

}
