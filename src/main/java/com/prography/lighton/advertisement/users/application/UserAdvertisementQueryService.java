package com.prography.lighton.advertisement.users.application;

import com.prography.lighton.advertisement.common.domain.entity.enums.Position;
import com.prography.lighton.advertisement.users.infrastructure.UserAdvertisementRepository;
import com.prography.lighton.advertisement.users.presentation.response.GetAdvertisementResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAdvertisementQueryService {

    private final UserAdvertisementRepository userAdvertisementRepository;

    public GetAdvertisementResponseDTO getAdvertisementsByPosition(Position position) {
        return GetAdvertisementResponseDTO.of(
                userAdvertisementRepository.findAllByPositionOrderByDisplayOrder(position));
    }
}
