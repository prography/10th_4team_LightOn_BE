package com.prography.lighton.member.users.infrastructure.repository;

import com.prography.lighton.member.common.domain.entity.association.PreferredGenre;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferredGenreRepository extends JpaRepository<PreferredGenre, Long> {

    List<PreferredGenre> findAllByMemberId(Long memberId);

    void deleteAllByMemberId(Long memberId);
}
