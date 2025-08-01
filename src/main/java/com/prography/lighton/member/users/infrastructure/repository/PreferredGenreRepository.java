package com.prography.lighton.member.users.infrastructure.repository;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.domain.entity.association.PreferredGenre;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferredGenreRepository extends JpaRepository<PreferredGenre, Long> {

    List<PreferredGenre> findAllByMember(Member member);

    @Modifying
    void deleteAllByMember(Member member);
}
