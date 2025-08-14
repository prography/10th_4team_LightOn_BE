package com.prography.lighton.common.fixture;

import static org.mockito.Mockito.mock;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.artist.common.domain.entity.vo.History;
import com.prography.lighton.common.domain.vo.RegionInfo;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;

public class ArtistTestFixture {

    public static final String ARTIST_NAME = "Test Artist";
    public static final String ARTIST_DESCRIPTION = "This is a test artist description.";
    public static final String ARTIST_PROFILE_IMAGE_URL = "https://example.com/test-artist-profile.jpg";
    public static final String ARTIST_PROOF_IMAGE_URL = "https://example.com/test-artist-proof.jpg";

    public static Artist createArtist(long id) {
        Artist artist = Artist.create(MemberTestFixture.createNormalMember(id), ARTIST_NAME,
                ARTIST_DESCRIPTION, ARTIST_PROFILE_IMAGE_URL, mock(RegionInfo.class),
                mock(History.class), ARTIST_PROOF_IMAGE_URL, List.of());
        ReflectionTestUtils.setField(artist, "id", id);
        return artist;
    }
}
