package com.prography.lighton.performance.domain.entity;

import com.prography.lighton.common.BaseEntity;
import com.prography.lighton.performance.domain.entity.association.PerformanceArtist;
import com.prography.lighton.performance.domain.entity.enums.Seat;
import com.prography.lighton.performance.domain.entity.enums.Type;
import com.prography.lighton.performance.domain.entity.vo.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private List<Seat> seats;

    @Column(nullable = false)
    private String notice;

    @Column(nullable = false)
    private Boolean isPaid;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long viewCount = 0L;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long likeCount = 0L;
}
