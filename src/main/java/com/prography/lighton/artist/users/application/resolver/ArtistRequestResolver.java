package com.prography.lighton.artist.users.application.resolver;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.artist.common.domain.entity.vo.History;
import com.prography.lighton.artist.users.presentation.dto.request.ArtistDTO;
import com.prography.lighton.artist.users.presentation.dto.request.HistoryDTO;
import com.prography.lighton.artist.users.presentation.dto.request.RegisterArtistMultipart;
import com.prography.lighton.artist.users.presentation.dto.request.UpdateArtistMultipart;
import com.prography.lighton.common.application.s3.S3UploadService;
import com.prography.lighton.common.domain.vo.RegionInfo;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.genre.infrastructure.cache.GenreCache;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.region.infrastructure.cache.RegionCache;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ArtistRequestResolver {

    private final RegionCache regionCache;
    private final GenreCache genreCache;
    private final S3UploadService uploadService;

    public Artist toNewEntity(Member member, RegisterArtistMultipart req) {
        String profileUrl = uploadService.uploadFile(req.profileImage(), member);
        String proofUrl = uploadService.uploadFile(req.proof(), member);
        List<String> activityUrls = uploadService.uploadFiles(req.activityPhotos(), member);

        ArtistDTO artistDTO = req.data().artist();
        HistoryDTO historyDTO = req.data().history();

        return Artist.create(
                member,
                artistDTO.name(),
                artistDTO.description(),
                profileUrl,
                resolveRegion(artistDTO),
                resolveHistory(historyDTO, activityUrls),
                proofUrl,
                resolveGenres(artistDTO)
        );
    }

    public UpdatePayload toUpdateEntity(Artist origin, UpdateArtistMultipart req) {
        Member member = origin.getMember();

        String profileUrl = replaceSingle(origin.getProfileImageUrl(), req.profileImage(), member);
        List<String> activityUrls = replaceMultiple(
                origin.getHistory().getActivityImages().toList(),
                req.activityPhotos(), member
        );

        ArtistDTO artistDTO = req.data().artist();
        HistoryDTO historyDTO = req.data().history();

        return new UpdatePayload(
                artistDTO.name(),
                artistDTO.description(),
                profileUrl,
                resolveRegion(artistDTO),
                resolveHistory(historyDTO, activityUrls),
                resolveGenres(artistDTO)
        );
    }

    private RegionInfo resolveRegion(ArtistDTO dto) {
        return regionCache.getRegionInfoByCode(dto.activityLocation());
    }

    private List<Genre> resolveGenres(ArtistDTO dto) {
        return genreCache.getGenresByNameOrThrow(dto.genre());
    }

    private History resolveHistory(HistoryDTO dto, List<String> urls) {
        return History.of(dto.bio(), urls);
    }

    private String replaceSingle(String originUrl, MultipartFile file,
                                 Member member) {
        if (file != null && !file.isEmpty()) {
            uploadService.deleteFile(originUrl);
            return uploadService.uploadFile(file, member);
        }
        return originUrl;
    }

    private List<String> replaceMultiple(List<String> originUrls,
                                         List<MultipartFile> files,
                                         Member member) {
        if (files != null && !files.isEmpty()) {
            uploadService.deleteFiles(originUrls);
            return uploadService.uploadFiles(files, member);
        }
        return originUrls;
    }

    public record UpdatePayload(
            String stageName,
            String description,
            String profileUrl,
            RegionInfo activityRegion,
            History history,
            List<Genre> genres
    ) {
    }
}
