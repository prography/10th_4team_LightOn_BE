package com.prography.lighton.performance.common.domain.entity;

import static lombok.AccessLevel.PROTECTED;

import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.common.domain.DomainValidator;
import com.prography.lighton.genre.domain.entity.Genre;
import com.prography.lighton.member.common.domain.entity.Member;
import com.prography.lighton.performance.common.domain.entity.enums.ApproveStatus;
import com.prography.lighton.performance.common.domain.entity.profile.PerformanceProfile;
import com.prography.lighton.performance.common.domain.entity.vo.Info;
import com.prography.lighton.performance.common.domain.entity.vo.Location;
import com.prography.lighton.performance.common.domain.entity.vo.Schedule;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@DiscriminatorValue("BUSKING")
@Getter
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE performance SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Busking extends Performance {

    private static final int UPDATE_DEADLINE_DAYS = 3;

    @Column(length = 100)
    private String artistName;

    @Column(length = 255)
    private
    String artistDescription;

    private Busking(
            Member performer,
            Info info,
            Schedule schedule,
            Location location,
            String proofUrl,
            List<Genre> genres,
            List<Artist> artists,
            String artistName,
            String artistDescription
    ) {
        super(
                performer,
                info,
                schedule,
                location,
                proofUrl,
                PerformanceProfile.busking(),
                genres,
                artists
        );
        this.artistName = artistName;
        this.artistDescription = artistDescription;
    }

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
        return new Busking(performer, info, schedule, location, proofUrl, genres, List.of(), artistName,
                artistDescription);
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
        Busking busking = new Busking(
                performer, info, schedule, location, proofUrl, genres,
                List.of(artist), artist.getStageName(), artist.getDescription()
        );
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
        updateCommonDetails(
                info,
                schedule,
                location,
                proofUrl,
                genres
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

        updateCommonDetails(
                info,
                schedule,
                location,
                proofUrl,
                genres
        );
    }
}
