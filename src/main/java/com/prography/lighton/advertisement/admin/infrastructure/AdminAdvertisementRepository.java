package com.prography.lighton.advertisement.admin.infrastructure;

import com.prography.lighton.advertisement.admin.infrastructure.exception.NoSuchAdvertisementException;
import com.prography.lighton.advertisement.common.domain.entity.Advertisement;
import com.prography.lighton.advertisement.common.domain.entity.enums.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminAdvertisementRepository extends JpaRepository<Advertisement, Long> {

    Long countByPosition(Position position);

    default Advertisement getById(Long advertisementId) {
        return findById(advertisementId)
                .orElseThrow(NoSuchAdvertisementException::new);
    }
}
