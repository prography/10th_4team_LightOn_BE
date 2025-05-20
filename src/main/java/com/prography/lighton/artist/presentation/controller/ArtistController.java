package com.prography.lighton.artist.presentation.controller;

import com.prography.lighton.artist.application.service.ArtistService;
import com.prography.lighton.artist.presentation.dto.request.RegisterArtistRequest;
import com.prography.lighton.artist.presentation.dto.request.UpdateArtistRequest;
import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.domain.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping
    public ResponseEntity<ApiResult<String>> registerArtist(@LoginMember Member member,
                                                            @Valid @RequestBody RegisterArtistRequest request) {
        artistService.registerArtist(member, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success());
    }

    @PutMapping
    public ResponseEntity<ApiResult<String>> updateArtist(@LoginMember Member member,
                                                          @Valid @RequestBody UpdateArtistRequest request) {
        artistService.updateArtist(member, request);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }
}
