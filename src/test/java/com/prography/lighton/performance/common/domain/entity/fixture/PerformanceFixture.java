package com.prography.lighton.performance.common.domain.entity.fixture;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.common.domain.vo.RegionInfo;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.enums.Seat;
import com.prography.lighton.performance.common.domain.entity.vo.Info;
import com.prography.lighton.performance.common.domain.entity.vo.Location;
import com.prography.lighton.performance.common.domain.entity.vo.Payment;
import com.prography.lighton.performance.common.domain.entity.vo.Schedule;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PerformanceFixture {

    public static final long DEFAULT_PERFORMER_ID = 1L;
    public static final long DEFAULT_ARTIST_ID = 10L;
    public static final long DEFAULT_GENRE_ID = 100L;
    public static final String DEFAULT_TITLE = "테스트 제목";
    public static final String DEFAULT_DESCRIPTION = "테스트 설명";
    public static final String DEFAULT_PLACE = "테스트 장소";
    public static final String DEFAULT_NOTICE = "테스트 주의사항";
    public static final String DEFAULT_POSTER = "테스트 포스터URL";
    public static final String DEFAULT_ACCOUNT = "123-456";
    public static final String DEFAULT_BANK = "테스트은행";
    public static final String DEFAULT_HOLDER = "홍길동";
    public static final int DEFAULT_FEE = 1000;
    public static final double DEFAULT_LATITUDE = 0.0;
    public static final double DEFAULT_LONGITUDE = 0.0;
    public static final String DEFAULT_PROOF_URL = "http://proof.url";
    public static final int DEFAULT_TOTAL_SEATS = 50;
    public static final String ARTIST_NAME = "아티스트 이름";
    public static final String ARTIST_DESCRIPTION = "아티스트 설명";


    public static Info defaultInfo() {
        return Info.of(
                DEFAULT_TITLE,
                DEFAULT_DESCRIPTION,
                DEFAULT_PLACE,
                DEFAULT_NOTICE,
                DEFAULT_POSTER
        );
    }

    public static Schedule defaultSchedule() {
        LocalDate start = LocalDate.now().plusDays(10);
        LocalDate end = start.plusDays(10);
        return Schedule.of(
                start,
                end,
                LocalTime.of(9, 0),
                LocalTime.of(18, 0)
        );
    }

    public static Location defaultLocation() {
        RegionInfo regionInfo =
                mock(RegionInfo.class);
        return Location.of(
                DEFAULT_LATITUDE,
                DEFAULT_LONGITUDE,
                regionInfo
        );
    }

    public static Payment defaultPayment() {
        return Payment.of(
                true,
                DEFAULT_ACCOUNT,
                DEFAULT_BANK,
                DEFAULT_HOLDER,
                DEFAULT_FEE
        );
    }

    public static List<Seat> defaultSeats() {
        return List.of(Seat.STANDING);
    }

    public static List<Artist> defaultArtists(Member performer) {
        return List.of(defaultArtist(performer));
    }

    public static List<Genre> defaultGenres() {
        return List.of(defaultGenre());
    }

    public static Performance defaultPerformance() {
        Member performer = defaultMember();
        return defaultPerformance(performer);
    }

    public static Performance defaultPerformance(Member performer) {
        return Performance.create(
                performer,
                defaultArtists(performer),
                defaultInfo(),
                defaultSchedule(),
                defaultLocation(),
                defaultPayment(),
                defaultSeats(),
                defaultGenres(),
                DEFAULT_PROOF_URL,
                DEFAULT_TOTAL_SEATS,
                ARTIST_NAME,
                ARTIST_DESCRIPTION
        );
    }

    public static Member defaultMember() {
        Member m = mock(Member.class);
        when(m.getId()).thenReturn(DEFAULT_PERFORMER_ID);
        return m;
    }

    public static Artist defaultArtist(Member performer) {
        Artist a = mock(Artist.class);
        when(a.getId()).thenReturn(DEFAULT_ARTIST_ID);
        when(a.getMember()).thenReturn(performer);
        return a;
    }

    public static Genre defaultGenre() {
        Genre g = mock(Genre.class);
        when(g.getId()).thenReturn(DEFAULT_GENRE_ID);
        return g;
    }
}
