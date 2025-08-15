package com.prography.lighton.member.common.infrastructure.repository;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.domain.entity.association.PreferredArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface PreferredArtistRepository extends JpaRepository<PreferredArtist, Long> {

    @Modifying
    void deleteAllByMember(Member member);
}
