package com.prography.lighton.artist.application.service;

import com.prography.lighton.artist.application.exception.ArtistRegistrationNotAllowedException;
import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.artist.domain.entity.enums.ApproveStatus;
import com.prography.lighton.artist.domain.entity.vo.History;
import com.prography.lighton.artist.infrastructure.repository.ArtistRepository;
import com.prography.lighton.artist.presentation.dto.ArtistRegisterRequest;
import com.prography.lighton.common.vo.RegionInfo;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.region.domain.resolver.RegionResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final RegionResolver regionResolver;

    @Transactional
    public void registerMember(Member member, ArtistRegisterRequest request) {
        validateArtistRegistration(member);

        RegionInfo activityRegion = regionResolver.resolve(request.artist().activityLocation());
        History history = History.of(request.history().bio(), request.history().activityPhotos());

        Artist artist = Artist.create(
                member,
                request.artist().name(),
                request.artist().description(),
                activityRegion,
                history,
                request.proof()
        );

        artistRepository.save(artist);
    }

    private void validateArtistRegistration(Member member) {
        artistRepository.findByMember(member)
                .filter(artist -> artist.getApproveStatus() != ApproveStatus.REJECTED)
                .ifPresent(artist -> {
                    throw new ArtistRegistrationNotAllowedException();
                });
    }
}
