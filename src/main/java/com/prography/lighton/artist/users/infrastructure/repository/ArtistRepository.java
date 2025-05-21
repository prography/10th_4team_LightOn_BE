package com.prography.lighton.artist.users.infrastructure.repository;

import com.prography.lighton.artist.users.application.exception.NoSuchArtistException;
import com.prography.lighton.artist.users.domain.entity.Artist;
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
                where a.id = :id
                  and a.status = true
            """)
    Optional<Artist> findById(Long id);

    @Query("""
                select a from Artist a
                join fetch a.genres ag
                where a.member = :member
                  and a.status = true
            """)
    Optional<Artist> findByMemberWithGenres(@Param("member") Member member);


    default Artist getByMember(Member member) {
        return findByMemberWithGenres(member)
                .orElseThrow(NoSuchArtistException::new);
    }

    default Artist getById(Long artistId) {
        return findById(artistId)
                .orElseThrow(NoSuchArtistException::new);
    }
}
