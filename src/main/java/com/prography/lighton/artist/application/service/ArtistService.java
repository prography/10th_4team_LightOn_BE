package com.prography.lighton.artist.application.service;

import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.artist.domain.entity.vo.History;
import com.prography.lighton.artist.infrastructure.repository.ArtistRepository;
import com.prography.lighton.artist.presentation.dto.ArtistRegisterRequest;
import com.prography.lighton.artist.presentation.dto.ArtistUpdateRequest;
import com.prography.lighton.common.vo.RegionInfo;
import com.prography.lighton.genre.application.service.GenreService;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.region.domain.resolver.RegionResolver;
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
    private final RegionResolver regionResolver;

    public Artist getApprovedArtistByMember(Member member) {
        Artist artist = artistRepository.getByMember(member);
        artist.validateApproved();
        return artist;
    }
    
    @Transactional
    public void registerArtist(Member member, ArtistRegisterRequest request) {
        artistRepository.findByMember(member)
                .ifPresent(Artist::validateCreatable);

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
    public void updateArtist(Member member, ArtistUpdateRequest request) {
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
            ArtistRegisterRequest.ArtistDTO artistDto,
            ArtistRegisterRequest.HistoryDTO historyDto
    ) {
        RegionInfo activityRegion = regionResolver.resolve(artistDto.activityLocation());
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
