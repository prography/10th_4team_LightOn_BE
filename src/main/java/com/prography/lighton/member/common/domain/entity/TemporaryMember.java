package com.prography.lighton.member.common.domain.entity;

import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.common.domain.BaseEntity;
import com.prography.lighton.member.common.domain.entity.vo.Email;
import com.prography.lighton.member.common.domain.entity.vo.Password;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE temporary_member SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class TemporaryMember extends BaseEntity {

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialLoginType loginType;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isRegistered = false;

    public static TemporaryMember of(Email email, Password password) {
        return new TemporaryMember(email, password, SocialLoginType.DEFAULT, false);
    }

    public static TemporaryMember socialLoginMemberOf(Email email, SocialLoginType loginType) {
        return new TemporaryMember(email, Password.forSocialLogin(), loginType, false);
    }

    public void markAsRegistered() {
        this.isRegistered = true;
    }


    public Boolean isRegistered() {
        return isRegistered;
    }
}
