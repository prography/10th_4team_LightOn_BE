package com.prography.lighton.artist.infrastructure.repository;

import com.prography.lighton.artist.application.exception.NoSuchArtistException;
import com.prography.lighton.artist.domain.entity.Artist;
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
                join fetch ag.genre g
                where a.member = :member
                  and a.status = true
                  and g.status = true
            """)
    Optional<Artist> findWithGenresByMember(@Param("member") Member member);

    default Artist getByMember(Member member) {
        return findWithGenresByMember(member)
                .orElseThrow(NoSuchArtistException::new);
    }
}
