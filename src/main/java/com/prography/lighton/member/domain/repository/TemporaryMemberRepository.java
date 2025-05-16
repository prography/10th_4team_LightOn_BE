package com.prography.lighton.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prography.lighton.member.domain.entity.TemporaryMember;

public interface TemporaryMemberRepository extends JpaRepository<TemporaryMember, Long> {

	@Query("SELECT COUNT(m) > 0 FROM TemporaryMember m WHERE m.email.value = :email")
	boolean existsByEmail(@Param("email") String email);
}
