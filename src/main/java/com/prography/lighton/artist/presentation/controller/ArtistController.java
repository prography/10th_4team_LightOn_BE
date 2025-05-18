package com.prography.lighton.artist.presentation.controller;

import com.prography.lighton.artist.presentation.dto.ArtistRegisterRequest;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    @PostMapping
    public ResponseEntity<ApiResult<String>> registerArtist(@Valid @RequestBody ArtistRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success());
    }
}
