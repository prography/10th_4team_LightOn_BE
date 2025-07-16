package com.prography.lighton.artist.users.application.resolver;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.artist.common.domain.entity.vo.History;
import com.prography.lighton.artist.users.presentation.dto.request.ArtistDTO;
import com.prography.lighton.artist.users.presentation.dto.request.HistoryDTO;
import com.prography.lighton.artist.users.presentation.dto.request.RegisterArtistMultipart;
import com.prography.lighton.common.application.s3.S3UploadService;
import com.prography.lighton.common.domain.vo.RegionInfo;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.genre.infrastructure.cache.GenreCache;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.region.infrastructure.cache.RegionCache;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArtistRequestResolver {

    private final RegionCache regionCache;
    private final GenreCache genreCache;
    private final S3UploadService uploadService;

    public Artist toNewEntity(Member member,
                              RegisterArtistMultipart req) {
        String profileUrl = uploadService.uploadFile(req.profileImage(), member);
        String proofUrl = uploadService.uploadFile(req.proof(), member);
        List<String> activityUrls = uploadService.uploadFiles(req.activityPhotos(), member);

        ArtistDTO artistDTO = req.data().artist();
        HistoryDTO historyDTO = req.data().history();

        RegionInfo region = regionCache.getRegionInfoByCode(artistDTO.activityLocation());
        List<Genre> genres = genreCache.getGenresByNameOrThrow(artistDTO.genre());
        History history = History.of(historyDTO.bio(), activityUrls);

        return Artist.create(member,
                artistDTO.name(),
                artistDTO.description(),
                profileUrl,
                region,
                history,
                proofUrl,
                genres);
    }

}
