package com.prography.lighton.artist.users.application.service;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.artist.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.artist.users.application.resolver.ArtistRequestResolver;
import com.prography.lighton.artist.users.infrastructure.repository.ArtistRepository;
import com.prography.lighton.artist.users.presentation.dto.request.RegisterArtistMultipart;
import com.prography.lighton.artist.users.presentation.dto.request.UpdateArtistMultipart;
import com.prography.lighton.artist.users.presentation.dto.response.ArtistCheckResponseDTO;
import com.prography.lighton.artist.users.presentation.dto.response.GetMyArtistInfoResponse;
import com.prography.lighton.member.common.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistRequestResolver resolver;

    public Artist getApprovedArtistByMember(Member member) {
        Artist artist = artistRepository.getByMember(member);
        artist.isValidApproved();
        return artist;
    }

    public GetMyArtistInfoResponse getMyArtistInfo(Member member) {
        Artist artist = artistRepository.getByMember(member);
        return GetMyArtistInfoResponse.of(artist.getStageName(), artist.getDescription());
    }

    @Transactional
    public void registerArtist(Member member, RegisterArtistMultipart request) {
        artistRepository.findByMember(member)
                .ifPresent(Artist::isValidRecreatable);

        Artist artist = resolver.toNewEntity(member, request);
        artistRepository.save(artist);
    }

    @Transactional
    public void updateArtist(Member member, UpdateArtistMultipart request) {
        Artist artist = getApprovedArtistByMember(member);

        var data = resolver.toUpdateEntity(artist, request);
        artist.update(data.stageName(),
                data.description(),
                data.profileUrl(),
                data.activityRegion(),
                data.history(),
                data.genres());
    }

    public void inactiveByMember(Member member) {
        artistRepository.deleteByMember(member);
    }

    public ArtistCheckResponseDTO isArtist(Member member) {
        return ArtistCheckResponseDTO.of(
                artistRepository.existsByMemberAndApproveStatus(member, ApproveStatus.APPROVED));
    }
}
