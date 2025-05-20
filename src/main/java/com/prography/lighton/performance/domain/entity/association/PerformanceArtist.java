package com.prography.lighton.performance.domain.entity.association;


import com.prography.lighton.artist.domain.entity.Artist;
import com.prography.lighton.common.domain.BaseEntity;
import com.prography.lighton.performance.domain.entity.Performance;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE performance_artist SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class PerformanceArtist extends BaseEntity {

    @ManyToOne(optional = false)
    private Artist artist;

    @ManyToOne(optional = false)
    private Performance performance;
}
