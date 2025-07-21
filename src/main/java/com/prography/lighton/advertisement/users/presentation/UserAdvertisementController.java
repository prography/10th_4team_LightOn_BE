package com.prography.lighton.advertisement.users.presentation;

import com.prography.lighton.advertisement.common.domain.entity.enums.Position;
import com.prography.lighton.advertisement.users.application.UserAdvertisementQueryService;
import com.prography.lighton.advertisement.users.presentation.response.GetAdvertisementResponseDTO;
import com.prography.lighton.common.utils.ApiUtils;
import com.prography.lighton.common.utils.ApiUtils.ApiResult;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members/advertisements")
@RequiredArgsConstructor
public class UserAdvertisementController {

    private final UserAdvertisementQueryService userAdvertisementQueryService;

    @GetMapping
    public ResponseEntity<ApiResult<GetAdvertisementResponseDTO>> getAdvertisementsByPosition(
            @RequestParam @NotNull Position position
    ) {
        return ResponseEntity.ok(ApiUtils.success(userAdvertisementQueryService.getAdvertisementsByPosition(position)));
    }
}
