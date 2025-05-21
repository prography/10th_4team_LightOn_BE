package com.prography.lighton.performance.domain.entity.association;


import com.prography.lighton.artist.common.domain.entity.Artist;
import com.prography.lighton.common.domain.BaseEntity;
import com.prography.lighton.performance.domain.entity.Performance;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE performance_artist SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class PerformanceArtist extends BaseEntity {

    @ManyToOne(optional = false)
    private Artist artist;

    @ManyToOne(optional = false)
    private Performance performance;

    public static List<PerformanceArtist> createListFor(Performance performance, List<Artist> artists) {
        return artists.stream()
                .map(artist -> create(performance, artist))
                .toList();
    }


    private static PerformanceArtist create(Performance performance, Artist artist) {
        return new PerformanceArtist(artist, performance);
    }

}
