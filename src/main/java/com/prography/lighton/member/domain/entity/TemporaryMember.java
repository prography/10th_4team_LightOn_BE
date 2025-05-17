package com.prography.lighton.member.domain.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.prography.lighton.common.BaseEntity;
import com.prography.lighton.member.domain.entity.vo.Email;
import com.prography.lighton.member.domain.entity.vo.Password;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE temporary_member SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class TemporaryMember extends BaseEntity {

	@Embedded
	private Email email;

	@Embedded
	private Password password;

	@Column(nullable = false)
	@ColumnDefault("false")
	private Boolean isRegistered = false;

	public static TemporaryMember of(Email email, Password password) {
		return new TemporaryMember(email, password, false);
	}

	public void markAsRegistered() {
		this.isRegistered = true;
	}

	public Email getEmail() {
		return email;
	}

	public Password getPassword() {
		return password;
	}

	public Boolean isRegistered() {
		return isRegistered;
	}
}
