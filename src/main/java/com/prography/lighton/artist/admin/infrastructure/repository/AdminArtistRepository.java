package com.prography.lighton.artist.admin.infrastructure.repository;

import com.prography.lighton.artist.users.domain.entity.Artist;
import com.prography.lighton.artist.users.domain.entity.enums.ApproveStatus;
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
                join fetch ag.genre g
                where a.id = :id
                  and a.approveStatus = :approveStatus
                  and a.status = true
            """)
    Optional<Artist> findByIdAndApproveStatus(Long id, ApproveStatus approveStatus);

    @Query(value = """
            select distinct a from Artist a
            join fetch a.genres ag
            join fetch ag.genre g
            where a.approveStatus = :approveStatus
              and a.status = true
            """,
            countQuery = """
                    select count(a) from Artist a
                    where a.approveStatus = :approveStatus
                      and a.status = true
                    """)
    Page<Artist> findByApproveStatus(@Param("approveStatus") ApproveStatus approveStatus, Pageable pageable);

    @Query(value = """
            select distinct a from Artist a
            join fetch a.genres ag
            join fetch ag.genre g
            where a.approveStatus != :approveStatus
              and a.status = true
            """,
            countQuery = """
                    select count(a) from Artist a
                    where a.approveStatus != :approveStatus
                      and a.status = true
                    """)
    Page<Artist> findUnapprovedArtists(@Param("approveStatus") ApproveStatus approveStatus, Pageable pageable);

}
