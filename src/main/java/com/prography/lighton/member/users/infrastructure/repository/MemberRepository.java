package com.prography.lighton.member.users.infrastructure.repository;

import com.prography.lighton.auth.domain.enums.SocialLoginType;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.member.common.domain.entity.vo.Email;
import com.prography.lighton.member.common.domain.entity.vo.Phone;
import com.prography.lighton.member.common.domain.exception.NoSuchMemberException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(Email email);

    boolean existsByPhone(Phone phone);

    @Query("SELECT COUNT(m) > 0 FROM Member m WHERE m.email.value = :email and m.status = true")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT COUNT(m) > 0 FROM Member m WHERE m.email.value = :email and m.loginType <> :loginType and m.status = true")
    boolean existsConflictingLoginTypeByEmail(String email, SocialLoginType loginType);


    default Member getMemberByEmail(Email email) {
        return findByEmail(email)
                .orElseThrow(NoSuchMemberException::new);
    }

}
