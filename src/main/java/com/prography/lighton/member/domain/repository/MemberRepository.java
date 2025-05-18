package com.prography.lighton.member.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.member.domain.entity.vo.Email;
import com.prography.lighton.member.domain.entity.vo.Phone;
import com.prography.lighton.member.exception.NoSuchMemberException;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(Email email);

	boolean existsByPhone(Phone phone);

	default Member getMemberByEmail(Email email) {
		return findByEmail(email)
				.orElseThrow(NoSuchMemberException::new);
	}

}
