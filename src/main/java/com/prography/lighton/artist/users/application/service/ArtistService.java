package com.prography.lighton.artist.users.application.service;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.artist.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.artist.users.application.exception.NoSuchArtistException;
import com.prography.lighton.artist.users.application.resolver.ArtistRequestResolver;
import com.prography.lighton.artist.users.infrastructure.repository.ArtistRepository;
import com.prography.lighton.artist.users.presentation.dto.request.RegisterArtistMultipart;
import com.prography.lighton.artist.users.presentation.dto.request.UpdateArtistMultipart;
import com.prography.lighton.artist.users.presentation.dto.response.ArtistCheckResponseDTO;
import com.prography.lighton.member.common.domain.entity.Member;
import java.util.List;
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

    public ArtistCheckResponseDTO isArtist(Member member) {
        return ArtistCheckResponseDTO.of(
                artistRepository.existsByMemberAndApproveStatus(member, ApproveStatus.APPROVED));
    }
}
