package com.prography.lighton.performance.domain.entity;

import com.prography.lighton.common.BaseEntity;
import com.prography.lighton.performance.domain.entity.association.PerformanceArtist;
import com.prography.lighton.performance.domain.entity.enums.Seat;
import com.prography.lighton.performance.domain.entity.enums.Type;
import com.prography.lighton.performance.domain.entity.vo.Info;
import com.prography.lighton.performance.domain.entity.vo.Location;
import com.prography.lighton.performance.domain.entity.vo.Payment;
import com.prography.lighton.performance.domain.entity.vo.Schedule;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE performance SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Performance extends BaseEntity {

    @OneToMany(mappedBy = "performance")
    @NotEmpty
    private List<PerformanceArtist> artists = new ArrayList<>();

    @Embedded
    private Info info;

    @Embedded
    private Schedule schedule;

    @Embedded
    private Location location;

    @Embedded
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ElementCollection(targetClass = Seat.class)
    @CollectionTable(
            name = "performance_seat",
            joinColumns = @JoinColumn(name = "performance_id")
    )
    @Column(name = "seat")
    @Enumerated(EnumType.STRING)
    private List<Seat> seats = new ArrayList<>();

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long viewCount = 0L;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long likeCount = 0L;

    @OneToMany(mappedBy = "performance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PerformanceGenre> genres = new ArrayList<>();
}
