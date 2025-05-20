package com.prography.lighton.artist.application.service;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import com.prography.lighton.artist.application.exception.NoSuchArtistException;
import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.artist.domain.entity.vo.History;
import com.prography.lighton.artist.infrastructure.repository.ArtistRepository;
import com.prography.lighton.artist.presentation.dto.request.ArtistDTO;
import com.prography.lighton.artist.presentation.dto.request.HistoryDTO;
import com.prography.lighton.artist.presentation.dto.request.RegisterArtistRequest;
import com.prography.lighton.artist.presentation.dto.request.UpdateArtistRequest;
import com.prography.lighton.common.domain.vo.RegionInfo;
import com.prography.lighton.genre.application.service.GenreService;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.region.infrastructure.cache.RegionCache;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final GenreService genreService;
    private final RegionCache regionCache;

    public Artist getApprovedArtistByMember(Member member) {
        Artist artist = artistRepository.getByMember(member);
        artist.isValidApproved();
        return artist;
    }

    public List<Artist> getApprovedArtistsByIds(List<Long> artistIds) {
        return artistRepository.findAllById(artistIds).stream()
                .peek(Artist::isValidApproved)
                .collect(collectingAndThen(toList(), list -> {
                    if (list.size() != artistIds.size()) {
                        throw new NoSuchArtistException();
                    }
                    return list;
                }));
    }


    @Transactional
    public void registerArtist(Member member, RegisterArtistRequest request) {
        artistRepository.findByMember(member)
                .ifPresent(Artist::isValidRecreatable);

        var data = toArtistData(request.artist(), request.history());
        Artist artist = Artist.create(
                member,
                request.artist().name(),
                request.artist().description(),
                data.activityRegion(),
                data.history(),
                request.proof(),
                data.genres()
        );

        artistRepository.save(artist);
    }

    @Transactional
    public void updateArtist(Member member, UpdateArtistRequest request) {
        Artist artist = getApprovedArtistByMember(member);

        var data = toArtistData(request.artist(), request.history());
        artist.update(
                request.artist().name(),
                request.artist().description(),
                data.activityRegion(),
                data.history(),
                data.genres()
        );
    }

    private ArtistData toArtistData(
            ArtistDTO artistDto,
            HistoryDTO historyDto
    ) {
        RegionInfo activityRegion = regionCache.getRegionInfoByCode(artistDto.activityLocation());
        History history = History.of(historyDto.bio(), historyDto.activityPhotos());
        List<Genre> genres = genreService.getGenresOrThrow(artistDto.genre());
        return new ArtistData(activityRegion, history, genres);
    }

    private static record ArtistData(
            RegionInfo activityRegion,
            History history,
            List<Genre> genres
    ) {
    }
}
