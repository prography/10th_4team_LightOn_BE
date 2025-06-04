package com.prography.lighton.announcement.admin.infrastructure;

import com.prography.lighton.announcement.admin.application.exception.NoSuchAnnouncementException;
import com.prography.lighton.announcement.common.domain.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminAnnouncementRepository extends JpaRepository<Announcement, Long> {

    default Announcement getById(Long announcementId) {
        return findById(announcementId)
                .orElseThrow(NoSuchAnnouncementException::new);
    }
}
