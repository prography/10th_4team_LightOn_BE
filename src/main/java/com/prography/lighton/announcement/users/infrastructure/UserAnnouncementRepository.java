package com.prography.lighton.announcement.users.infrastructure;

import com.prography.lighton.announcement.admin.application.exception.NoSuchAnnouncementException;
import com.prography.lighton.announcement.common.domain.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnnouncementRepository extends JpaRepository<Announcement, Long> {

    default Announcement getById(Long announcementId) {
        return findById(announcementId)
                .orElseThrow(NoSuchAnnouncementException::new);
    }

    Page<Announcement> findAllByOrderByCreatedAtDesc(org.springframework.data.domain.Pageable pageable);
}
