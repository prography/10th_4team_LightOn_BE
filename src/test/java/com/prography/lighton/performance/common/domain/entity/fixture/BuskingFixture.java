package com.prography.lighton.performance.common.domain.entity.fixture;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Busking;

public class BuskingFixture {

    public static final String ARTIST_NAME = "아티스트 이름";
    public static final String ARTIST_DESCRIPTION = "아티스트 설명";

    public static Busking defaultBuskingByUser() {
        Member performer = PerformanceFixture.defaultMember();
        return Busking.createByUser(
                performer,
                PerformanceFixture.defaultInfo(),
                PerformanceFixture.defaultSchedule(),
                PerformanceFixture.defaultLocation(),
                PerformanceFixture.defaultGenres(),
                PerformanceFixture.DEFAULT_PROOF_URL,
                ARTIST_NAME,
                ARTIST_DESCRIPTION
        );
    }

    public static Busking defaultBuskingByUser(Member performer) {
        return Busking.createByUser(
                performer,
                PerformanceFixture.defaultInfo(),
                PerformanceFixture.defaultSchedule(),
                PerformanceFixture.defaultLocation(),
                PerformanceFixture.defaultGenres(),
                PerformanceFixture.DEFAULT_PROOF_URL,
                ARTIST_NAME,
                ARTIST_DESCRIPTION
        );
    }

    public static Busking defaultBuskingByArtist() {
        Member performer = PerformanceFixture.defaultMember();
        Artist artist = PerformanceFixture.defaultArtist(performer);
        return Busking.createByArtist(
                performer,
                PerformanceFixture.defaultInfo(),
                PerformanceFixture.defaultSchedule(),
                PerformanceFixture.defaultLocation(),
                PerformanceFixture.defaultGenres(),
                PerformanceFixture.DEFAULT_PROOF_URL,
                artist
        );
    }

    public static Busking defaultBuskingByArtist(Member performer, Artist artist) {
        return Busking.createByArtist(
                performer,
                PerformanceFixture.defaultInfo(),
                PerformanceFixture.defaultSchedule(),
                PerformanceFixture.defaultLocation(),
                PerformanceFixture.defaultGenres(),
                PerformanceFixture.DEFAULT_PROOF_URL,
                artist
        );
    }
}
