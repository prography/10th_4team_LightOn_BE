package com.prography.lighton.member.domain.entity.association;

import com.prography.lighton.common.domain.BaseEntity;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.domain.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PreferredGenre extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;
}
