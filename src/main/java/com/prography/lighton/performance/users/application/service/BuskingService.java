package com.prography.lighton.performance.users.application.service;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.artist.users.application.service.ArtistService;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Busking;
import com.prography.lighton.performance.users.application.resolver.PerformanceResolver;
import com.prography.lighton.performance.users.infrastructure.repository.PerformanceRepository;
import com.prography.lighton.performance.users.presentation.dto.ArtistBuskingRegisterRequest;
import com.prography.lighton.performance.users.presentation.dto.ArtistBuskingUpdateRequest;
import com.prography.lighton.performance.users.presentation.dto.UserBuskingRegisterRequest;
import com.prography.lighton.performance.users.presentation.dto.UserBuskingUpdateRequest;
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

    public Busking getApprovedBuskingById(Long id) {
        Busking performance = performanceRepository.getByBuskingId(id);
        performance.validateApproved();
        return performance;
    }

    @Transactional
    public void registerBuskingByUser(Member member, UserBuskingRegisterRequest request) {
        var data = performanceResolver.toBuskingData(member, request.info(), request.schedule());
        Busking busking = Busking.createByUser(member, data.info(), data.schedule(),
                data.location(), data.genres(), request.proof(), request.artistName(), request.artistDescription());
        performanceRepository.save(busking);
    }

    @Transactional
    public void registerBuskingByArtist(Member member, ArtistBuskingRegisterRequest request) {
        Artist artist = artistService.getApprovedArtistByMember(member);
        var data = performanceResolver.toBuskingData(member, request.info(), request.schedule());
        Busking busking = Busking.createByArtist(member, data.info(), data.schedule(),
                data.location(), data.genres(), request.proof(), artist);
        performanceRepository.save(busking);
    }

    @Transactional
    public void updateBuskingByUser(Member member, Long buskingId, UserBuskingUpdateRequest request) {
        Busking busking = getApprovedBuskingById(buskingId);
        var data = performanceResolver.toBuskingData(member, request.info(), request.schedule());
        busking.updateByUser(member, data.info(), data.schedule(), data.location(), data.genres(), request.proof(),
                request.artistName(), request.artistDescription());
    }

    @Transactional
    public void updateBuskingByArtist(Member member, Long buskingId, ArtistBuskingUpdateRequest request) {
        Busking busking = getApprovedBuskingById(buskingId);
        var data = performanceResolver.toBuskingData(member, request.info(), request.schedule());
        busking.updateByArtist(member, data.info(), data.schedule(), data.location(), data.genres(), request.proof());
    }

    @Transactional
    public void cancelBusking(Member member, Long buskingId) {
        Busking busking = getApprovedBuskingById(buskingId);
        busking.cancel(member);
    }
}
