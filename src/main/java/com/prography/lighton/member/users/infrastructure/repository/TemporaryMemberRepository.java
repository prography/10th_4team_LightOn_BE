package com.prography.lighton.member.users.infrastructure.repository;

import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.member.common.domain.entity.TemporaryMember;
import com.prography.lighton.member.common.domain.entity.vo.Email;
import com.prography.lighton.member.common.domain.exception.NoSuchMemberException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TemporaryMemberRepository extends JpaRepository<TemporaryMember, Long> {

    Optional<TemporaryMember> findByEmail(Email email);

    @Query("SELECT COUNT(m) > 0 FROM TemporaryMember m WHERE m.email.value = :email AND not m.isRegistered")
    boolean existsByEmailAndNotRegistered(@Param("email") String email);

    @Query("SELECT COUNT(m) > 0 FROM TemporaryMember m WHERE m.email.value = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT COUNT(m) > 0 FROM Member m WHERE m.email.value = :email and m.loginType <> :loginType and m.status = true")
    boolean existsConflictingLoginTypeByEmail(String email, SocialLoginType loginType);

    default TemporaryMember getById(final Long id) {
        return findById(id)
                .orElseThrow(NoSuchMemberException::new);
    }
}
