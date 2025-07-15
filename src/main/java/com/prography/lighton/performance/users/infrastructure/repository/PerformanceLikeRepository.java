package com.prography.lighton.performance.users.infrastructure.repository;

import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.Performance;
import com.prography.lighton.performance.common.domain.entity.PerformanceLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface PerformanceLikeRepository extends JpaRepository<PerformanceLike, Long> {

    Optional<PerformanceLike> findByMemberAndPerformance(Member member, Performance performance);

    boolean existsByMemberAndPerformance(Member member, Performance performance);


    @Modifying
    void deleteAllByMember(Member member);

}

