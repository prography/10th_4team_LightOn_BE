package com.prography.lighton.artist.infrastructure.repository;

import com.prography.lighton.artist.application.exception.NoSuchArtistException;
import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.member.domain.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Optional<Artist> findByMember(Member member);

    default Artist getByMember(Member member) {
        return findByMember(member)
                .orElseThrow(NoSuchArtistException::new);
    }
}
