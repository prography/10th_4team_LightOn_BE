package com.prography.lighton.member.admin.infrastructure;

import com.prography.lighton.member.common.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMemberRepository extends JpaRepository<Member, Long> {
}
