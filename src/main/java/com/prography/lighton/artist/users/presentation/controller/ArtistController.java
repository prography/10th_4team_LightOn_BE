package com.prography.lighton.artist.users.presentation.controller;

import com.prography.lighton.artist.users.application.service.ArtistService;
import com.prography.lighton.artist.users.presentation.dto.request.RegisterArtistMultipart;
import com.prography.lighton.artist.users.presentation.dto.request.RegisterArtistRequest;
import com.prography.lighton.artist.users.presentation.dto.request.UpdateArtistMultipart;
import com.prography.lighton.artist.users.presentation.dto.request.UpdateArtistRequest;
import com.prography.lighton.artist.users.presentation.dto.response.ArtistCheckResponseDTO;
import com.prography.lighton.common.annotation.LoginMember;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import com.prography.lighton.member.common.domain.entity.Member;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping
    public ResponseEntity<ApiResult<String>> registerArtist(
            @LoginMember Member member,

            @Valid @RequestPart("data")
            RegisterArtistRequest data,

            @NotNull @RequestPart("profileImage")
            MultipartFile profileImage,

            @NotNull @RequestPart("proof")
            MultipartFile proof,

            @Size(max = 5)
            @RequestPart(value = "activityPhotos")
            List<MultipartFile> activityPhotos) {
        RegisterArtistMultipart request = new RegisterArtistMultipart(data, profileImage, proof, activityPhotos);
        artistService.registerArtist(member, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success());
    }

    @PutMapping
    public ResponseEntity<ApiResult<String>> updateArtist(@LoginMember Member member,
                                                          @Valid @RequestPart("data")
                                                          UpdateArtistRequest data,

                                                          @RequestPart(value = "profileImage", required = false)
                                                          MultipartFile profileImage,

                                                          @Size(max = 5)
                                                          @RequestPart(value = "activityPhotos", required = false)
                                                          List<MultipartFile> activityPhoto) {
        UpdateArtistMultipart request = new UpdateArtistMultipart(data, profileImage, activityPhoto);
        artistService.updateArtist(member, request);
        return ResponseEntity.ok()
                .body(ApiUtils.success());
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResult<ArtistCheckResponseDTO>> checkIsArtist(@LoginMember Member member) {
        return ResponseEntity.ok(ApiUtils.success(artistService.isArtist(member)));
    }
}
