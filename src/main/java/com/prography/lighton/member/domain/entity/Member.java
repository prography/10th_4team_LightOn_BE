package com.prography.lighton.member.domain.entity;

import com.prography.lighton.common.BaseEntity;
import com.prography.lighton.common.vo.RegionInfo;
import com.prography.lighton.member.domain.entity.association.PreferredArtist;
import com.prography.lighton.member.domain.entity.enums.Authority;
import com.prography.lighton.member.domain.entity.vo.Email;
import com.prography.lighton.member.domain.entity.vo.MarketingAgreement;
import com.prography.lighton.member.domain.entity.vo.Password;
import com.prography.lighton.member.domain.entity.vo.Phone;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

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
    private RegionInfo preferredRegion;

    @Embedded
    private Phone phone;

    @Embedded
    private MarketingAgreement marketingAgreement;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<PreferredArtist> preferredArtists;

    public static Member toNormalMember(Email email, Password password, RegionInfo preferredRegion,
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

    // 임시 구현 함수, 로그인 구현 시 사라질 예정
    public static Member withId(Long id) {
        Member member = new Member();
        member.setIdForTest(id);
        return member;
    }

    protected void setIdForTest(Long id) {
        try {
            Field idField = BaseEntity.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(this, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
