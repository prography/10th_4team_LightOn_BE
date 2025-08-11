package com.prography.lighton.performance.common.domain.entity;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.common.domain.DomainValidator;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.common.domain.entity.enums.Seat;
import com.prography.lighton.performance.common.domain.entity.enums.Type;
import com.prography.lighton.performance.common.domain.entity.vo.Info;
import com.prography.lighton.performance.common.domain.entity.vo.Location;
import com.prography.lighton.performance.common.domain.entity.vo.Payment;
import com.prography.lighton.performance.common.domain.entity.vo.Schedule;
import com.prography.lighton.performance.common.domain.entity.vo.SeatInventory;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@DiscriminatorValue("BUSKING")
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@SQLDelete(sql = "UPDATE performance SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Busking extends Performance {

    private static final int UPDATE_DEADLINE_DAYS = 3;

    @Column(length = 100)
    private String artistName;

    @Column(length = 255)
    private
    String artistDescription;

    public static Busking createByUser(
            Member performer,
            Info info,
            Schedule schedule,
            Location location,
            List<Genre> genres,
            String proofUrl,
            String artistName,
            String artistDescription
    ) {
        DomainValidator.requireNonBlank(artistName);
        DomainValidator.requireNonBlank(artistDescription);
        DomainValidator.requireNonBlank(proofUrl);

        Busking busking = new Busking(artistName, artistDescription);
        busking.initCommonFields(
                performer,
                info,
                schedule,
                location,
                Payment.free(),
                Type.BUSKING,
                Seat.STANDING,
                proofUrl,
                genres,
                SeatInventory.unlimited()
        );
        return busking;
    }

    public static Busking createByArtist(
            Member performer,
            Info info,
            Schedule schedule,
            Location location,
            List<Genre> genres,
            String proofUrl,
            Artist artist
    ) {
        DomainValidator.requireNonBlank(proofUrl);

        Busking busking = new Busking(artist.getStageName(), artist.getDescription());
        busking.initCommonFields(
                performer,
                info,
                schedule,
                location,
                Payment.free(),
                Type.BUSKING,
                Seat.STANDING,
                proofUrl,
                genres,
                SeatInventory.unlimited()
        );
        busking.updateArtists(List.of(artist));
        busking.managePerformanceApplication(ApproveStatus.APPROVED);
        return busking;
    }

    @Override
    protected int updateDeadlineDays() {
        return UPDATE_DEADLINE_DAYS;
    }

    public void updateByArtist(
            Member performer,
            Info info,
            Schedule schedule,
            Location location,
            List<Genre> genres,
            String proofUrl
    ) {
        validatePerformer(performer);
        ensureUpdatableWindow();
        DomainValidator.requireNonBlank(proofUrl);
        initCommonFields(
                performer,
                info,
                schedule,
                location,
                Payment.free(),
                Type.BUSKING,
                Seat.STANDING,
                proofUrl,
                genres,
                SeatInventory.unlimited()
        );
    }


    public void updateByUser(
            Member performer,
            Info info,
            Schedule schedule,
            Location location,
            List<Genre> genres,
            String proofUrl,
            String artistName,
            String artistDescription
    ) {
        validatePerformer(performer);
        ensureUpdatableWindow();
        DomainValidator.requireNonBlank(proofUrl);
        DomainValidator.requireNonBlank(artistName);
        DomainValidator.requireNonBlank(artistDescription);

        this.artistName = artistName;
        this.artistDescription = artistDescription;

        initCommonFields(
                this.getPerformer(),
                info,
                schedule,
                location,
                Payment.free(),
                Type.BUSKING,
                Seat.STANDING,
                proofUrl,
                genres,
                SeatInventory.unlimited()
        );
    }
}
