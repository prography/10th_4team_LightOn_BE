package com.prography.lighton.artist.infrastructure.repository;

import com.prography.lighton.artist.application.exception.NoSuchArtistException;
import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.artist.domain.entity.enums.ApproveStatus;
import com.prography.lighton.member.domain.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Optional<Artist> findByMember(Member member);

    @Query("""
                select a from Artist a
                join fetch a.genres ag
                where a.member = :member
                  and a.status = true
            """)
    Optional<Artist> findByMemberWithGenres(@Param("member") Member member);

    Optional<Artist> findByIdAndApproveStatus(Long id, ApproveStatus approveStatus);

    default Artist getByMember(Member member) {
        return findByMemberWithGenres(member)
                .orElseThrow(NoSuchArtistException::new);
    }

    default Artist getByIdAndApproveStatus(Long artistId, ApproveStatus approveStatus) {
        return findByIdAndApproveStatus(artistId, approveStatus)
                .orElseThrow(NoSuchArtistException::new);
    }
}
