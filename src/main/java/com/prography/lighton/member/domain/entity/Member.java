package com.prography.lighton.member.domain.entity;

import com.prography.lighton.common.BaseEntity;
import com.prography.lighton.member.domain.entity.association.PreferredArtist;
import com.prography.lighton.member.domain.entity.enums.Authority;
import com.prography.lighton.member.domain.entity.vo.Email;
import com.prography.lighton.member.domain.entity.vo.MarketingAgreement;
import com.prography.lighton.member.domain.entity.vo.Password;
import com.prography.lighton.member.domain.entity.vo.Phone;
import com.prography.lighton.member.domain.entity.vo.PreferredRegion;
import com.prography.lighton.member.exception.InvalidMemberException;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
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
    private Password password;

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

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<PreferredArtist> preferredArtists;

    public static Member toNormalMember (Email email, Password password, PreferredRegion preferredRegion,
            String name, Phone phone, MarketingAgreement marketingAgreement) {
        return new Member(
                email,
                password,
                name,
                preferredRegion,
                phone,
                marketingAgreement,
                Authority.NORMAL,
                new ArrayList<>()
        );
    }
    // Member.java
    public void validatePassword(String rawPassword, PasswordEncoder encoder) {
        if (!this.password.matches(rawPassword, encoder)) {
            throw new InvalidMemberException("비밀번호가 일치하지 않습니다.");
        }
    }

}
