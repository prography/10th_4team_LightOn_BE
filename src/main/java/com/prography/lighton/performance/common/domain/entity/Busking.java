package com.prography.lighton.performance.common.domain.entity;

import static lombok.AccessLevel.PROTECTED;

import com.prography.lighton.common.domain.DomainValidator;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.enums.Type;
import com.prography.lighton.performance.common.domain.entity.vo.Info;
import com.prography.lighton.performance.common.domain.entity.vo.Location;
import com.prography.lighton.performance.common.domain.entity.vo.Payment;
import com.prography.lighton.performance.common.domain.entity.vo.Schedule;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("BUSKING")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Busking extends Performance {

    private static final int UPDATE_DEADLINE_DAYS = 3;
    private static final int CANCEL_DEADLINE_DAYS = 3;

    public static Busking create(
            Member performer,
            Info info,
            Schedule schedule,
            Location location,
            List<Genre> genres,
            String proofUrl
    ) {
        Busking busking = new Busking();
        busking.initCommonFields(
                performer,
                info,
                schedule,
                location,
                Payment.free(),
                Type.BUSKING,
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
            List<Genre> genres,
            String proofUrl
    ) {
        validatePerformer(performer);
        validateWithinAllowedPeriod(UPDATE_DEADLINE_DAYS);
        DomainValidator.requireNonBlank(proofUrl);
        initCommonFields(
                performer,
                info,
                schedule,
                location,
                Payment.free(),
                Type.BUSKING,
                proofUrl,
                genres
        );
    }
}
