package com.prography.lighton.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.member.domain.entity.vo.Phone;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByPhone(Phone phone);
}
