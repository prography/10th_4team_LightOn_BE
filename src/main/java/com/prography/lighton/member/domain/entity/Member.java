package com.prography.lighton.member.domain.entity;

import com.prography.lighton.common.BaseEntity;
import com.prography.lighton.member.domain.entity.association.PreferredArtist;
import com.prography.lighton.member.domain.entity.enums.Authority;
import com.prography.lighton.member.domain.entity.vo.Email;
import com.prography.lighton.member.domain.entity.vo.MarketingAgreement;
import com.prography.lighton.member.domain.entity.vo.Phone;
import com.prography.lighton.member.domain.entity.vo.PreferredRegion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE member SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Member extends BaseEntity {

    @Embedded
    private Email email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Embedded
    private PreferredRegion preferredRegion;

    @Embedded
    private Phone phone;

    @Embedded
    private MarketingAgreement marketingAgreement;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "member_id", fetch = FetchType.LAZY)
    private List<PreferredArtist> preferredArtists;
}
