package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.artist.users.application.service.ArtistService;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Busking;
import com.prography.lighton.performance.users.application.resolver.PerformanceResolver;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRepository;
import com.prography.lighton.performance.users.presentation.dto.request.RegisterArtistBuskingMultiPart;
import com.prography.lighton.performance.users.presentation.dto.request.RegisterUserBuskingMultiPart;
import com.prography.lighton.performance.users.presentation.dto.request.UpdateArtistBuskingMultiPart;
import com.prography.lighton.performance.users.presentation.dto.request.UpdateUserBuskingMultiPart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BuskingService {

    private final PerformanceRepository performanceRepository;
    private final PerformanceResolver performanceResolver;
    private final ArtistService artistService;

    @Transactional
    public void registerBuskingByUser(Member member, RegisterUserBuskingMultiPart request) {
        var data = performanceResolver.toNewBuskingData(member, request.data().info(), request.data().schedule(),
                request.posterImage(),
                request.proof());
        Busking busking = Busking.createByUser(member, data.info(), data.schedule(),
                data.location(), data.genres(), data.proofUrl(), request.data().artistName(),
                request.data().artistDescription());
        performanceRepository.save(busking);
    }

    @Transactional
    public void registerBuskingByArtist(Member member, RegisterArtistBuskingMultiPart request) {
        Artist artist = artistService.getApprovedArtistByMember(member);
        var data = performanceResolver.toNewBuskingData(member, request.data().info(), request.data().schedule(),
                request.posterImage(),
                request.proof());
        Busking busking = Busking.createByArtist(member, data.info(), data.schedule(),
                data.location(), data.genres(), data.proofUrl(), artist);
        performanceRepository.save(busking);
    }

    @Transactional
    public void updateBuskingByUser(Member member, Long buskingId, UpdateUserBuskingMultiPart request) {
        Busking busking = performanceRepository.getByBuskingId(buskingId);
        var data = performanceResolver.toUpdateBuskingData(member, busking, request.data().info(),
                request.data().schedule(),
                request.posterImage(),
                request.proof());
        busking.updateByUser(member, data.info(), data.schedule(), data.location(), data.genres(), data.proofUrl(),
                request.data().artistName(), request.data().artistDescription());
    }

    @Transactional
    public void updateBuskingByArtist(Member member, Long buskingId, UpdateArtistBuskingMultiPart request) {
        Busking busking = performanceRepository.getByBuskingId(buskingId);
        var data = performanceResolver.toUpdateBuskingData(member, busking, request.data().info(),
                request.data().schedule(),
                request.posterImage(), request.proof());
        busking.updateByArtist(member, data.info(), data.schedule(), data.location(), data.genres(), data.proofUrl());
    }

    @Transactional
    public void cancelBusking(Member member, Long buskingId) {
        Busking busking = performanceRepository.getByBuskingId(buskingId);
        busking.cancel(member);
    }
}
