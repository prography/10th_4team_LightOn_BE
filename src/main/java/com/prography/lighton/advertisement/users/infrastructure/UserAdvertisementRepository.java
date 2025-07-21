package com.prography.lighton.advertisement.users.infrastructure;

import com.prography.lighton.advertisement.common.domain.entity.Advertisement;
import com.prography.lighton.advertisement.common.domain.entity.enums.Position;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAdvertisementRepository extends JpaRepository<Advertisement, Long> {

    List<Advertisement> findAllByPositionOrderByDisplayOrder(Position position);

}
