package com.prography.lighton.performance.common.domain.entity;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.common.domain.DomainValidator;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.enums.Seat;
import com.prography.lighton.performance.common.domain.entity.enums.Type;
import com.prography.lighton.performance.common.domain.entity.vo.Info;
import com.prography.lighton.performance.common.domain.entity.vo.Location;
import com.prography.lighton.performance.common.domain.entity.vo.Payment;
import com.prography.lighton.performance.common.domain.entity.vo.Schedule;
import com.prography.lighton.performance.common.domain.exception.NotAuthorizedPerformanceException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@DiscriminatorValue("BUSKING")
@Getter
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE busking SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Busking extends Performance {

    private static final int UPDATE_DEADLINE_DAYS = 3;

    @ManyToOne(fetch = LAZY, optional = false)
    private Member performer;

    public static Busking create(
            Member performer,
            Info info,
            Schedule schedule,
            Location location,
            List<Seat> seats,
            List<Genre> genres,
            String proofUrl
    ) {
        Busking busking = new Busking();
        busking.performer = performer;
        busking.initCommonFields(
                info,
                schedule,
                location,
                Payment.free(),
                Type.BUSKING,
                seats,
                proofUrl,
                genres
        );
        return busking;
    }

    public void update(
            Member performer,
            Info info,
            Schedule schedule,
            Location location,
            List<Seat> seats,
            List<Genre> genres,
            String proofUrl
    ) {
        validatePerformer(performer);
        validateWithinAllowedPeriod(UPDATE_DEADLINE_DAYS);
        DomainValidator.requireNonBlank(proofUrl);
        initCommonFields(
                info,
                schedule,
                location,
                Payment.free(),
                Type.BUSKING,
                seats,
                proofUrl,
                genres
        );
    }

    private void validatePerformer(Member member) {
        if (!performer.equals(member)) throw new NotAuthorizedPerformanceException();
    }
}
