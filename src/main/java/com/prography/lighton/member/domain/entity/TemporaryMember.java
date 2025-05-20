package com.prography.lighton.member.domain.entity;

import com.prography.lighton.common.BaseEntity;
import com.prography.lighton.member.domain.entity.vo.Email;
import com.prography.lighton.member.domain.entity.vo.Password;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE temporary_member SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class TemporaryMember extends BaseEntity {

    @Getter
    @Embedded
    private Email email;

    @Getter
    @Embedded
    private Password password;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isRegistered = false;

    public static TemporaryMember of(Email email, Password password) {
        return new TemporaryMember(email, password, false);
    }

    public static TemporaryMember socialLoginMemberOf(Email email) {
        return new TemporaryMember(email, Password.forSocialLogin(), false);
    }

    public void markAsRegistered() {
        this.isRegistered = true;
    }

    public Boolean isRegistered() {
        return isRegistered;
    }
}
