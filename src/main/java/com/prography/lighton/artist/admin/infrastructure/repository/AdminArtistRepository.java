package com.prography.lighton.artist.admin.infrastructure.repository;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.artist.common.domain.entity.enums.ApproveStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminArtistRepository extends JpaRepository<Artist, Long> {

    @Query("""
                select a from Artist a
                join fetch a.genres ag
                where a.id = :id
                  and a.approveStatus = :approveStatus
            """)
    Optional<Artist> findByIdAndApproveStatus(Long id, ApproveStatus approveStatus);


    @Query(value = """
            select distinct a from Artist a
            join fetch a.genres ag
            join fetch ag.genre g
            where a.approveStatus in :statuses
            """,
            countQuery = """
                    select count(distinct a) from Artist a
                    where a.approveStatus in :statuses
                    """)
    Page<Artist> findByApproveStatuses(@Param("statuses") List<ApproveStatus> statuses, Pageable pageable);

    Long countByApproveStatus(ApproveStatus approveStatus);
}
