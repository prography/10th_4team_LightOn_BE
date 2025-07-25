package com.prography.lighton.member.common.domain.entity;

import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.common.domain.BaseEntity;
import com.prography.lighton.common.domain.vo.RegionInfo;
import com.prography.lighton.member.common.domain.entity.association.PreferredArtist;
import com.prography.lighton.member.common.domain.entity.association.PreferredGenre;
import com.prography.lighton.member.common.domain.entity.enums.Authority;
import com.prography.lighton.member.common.domain.entity.vo.Email;
import com.prography.lighton.member.common.domain.entity.vo.MarketingAgreement;
import com.prography.lighton.member.common.domain.entity.vo.Password;
import com.prography.lighton.member.common.domain.entity.vo.Phone;
import com.prography.lighton.member.common.domain.exception.InvalidMemberException;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE member SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Member extends BaseEntity {

    @Embedded
    @AttributeOverride(name = "value",
            column = @Column(name = "email", nullable = false, unique = true))
    private Email email;

    @Column(nullable = false)
    private Password password;

    @Column(nullable = false)
    private String name;

    @Embedded
    private RegionInfo preferredRegion;

    @Embedded
    private Phone phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialLoginType loginType;

    @Embedded
    private MarketingAgreement marketingAgreement;

    @Getter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<PreferredArtist> preferredArtists;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<PreferredGenre> preferredGenres;


    public static Member toNormalMember(Email email, Password password, RegionInfo preferredRegion,
                                        String name, Phone phone, SocialLoginType loginType,
                                        MarketingAgreement marketingAgreement) {
        return new Member(
                email,
                password,
                name,
                preferredRegion,
                phone,
                loginType,
                marketingAgreement,
                Authority.NORMAL,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    // Member.java
    public void validatePassword(String rawPassword, PasswordEncoder encoder) {
        if (!this.password.matches(rawPassword, encoder)) {
            throw new InvalidMemberException("비밀번호가 일치하지 않습니다.");
        }
    }

    public boolean isAdmin() {
        return this.authority == Authority.ADMIN;
    }

    public void editPreferredGenres(List<PreferredGenre> preferredGenres) {
        this.preferredGenres.clear();
        this.preferredGenres.addAll(preferredGenres);
    }

    public void withdraw() {
        this.phone = this.phone.withdrawMasked(this.getId());
        this.email = this.email.withdrawMasked(this.getId());
        this.delete();
    }
}
