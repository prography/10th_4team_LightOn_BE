package com.prography.lighton.advertisement.admin.presentation.dto.response;

import com.prography.lighton.advertisement.common.entity.Advertisement;
import com.prography.lighton.advertisement.common.entity.enums.Position;
import java.util.List;

public record GetAdvertisementResponseDTO(List<AdvertisementDTO> advertisements) {

    public static GetAdvertisementResponseDTO of(List<Advertisement> advertisements) {
        List<AdvertisementDTO> advertisementDTOs = advertisements.stream()
                .map(ad -> AdvertisementDTO.of(ad.getId(), ad.getPosition(), ad.getDisplayOrder(),
                        ad.getImageUrl(), ad.getLinkUrl()))
                .toList();

        return new GetAdvertisementResponseDTO(advertisementDTOs);
    }

    public record AdvertisementDTO(
            Long id,
            Position position,
            int displayOrder,
            String imageUrl,
            String linkUrl
    ) {
        public static AdvertisementDTO of(Long id, Position position, int displayOrder, String imageUrl,
                                          String linkUrl) {
            return new AdvertisementDTO(id, position, displayOrder, imageUrl, linkUrl);
        }
    }

}
