package com.prography.lighton.artist.application.service;

import com.prography.lighton.artist.infrastructure.repository.ArtistRepository;
import com.prography.lighton.artist.presentation.dto.ArtistRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Transactional
    public void registerMember(ArtistRegisterRequest request) {

    }
}
