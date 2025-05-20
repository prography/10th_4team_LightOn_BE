package com.prography.lighton.member.domain.entity.association;


import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.common.BaseEntity;
import com.prography.lighton.member.domain.entity.Member;
import jakarta.persistence.*;

@Entity
public class PreferredArtist extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;
}
