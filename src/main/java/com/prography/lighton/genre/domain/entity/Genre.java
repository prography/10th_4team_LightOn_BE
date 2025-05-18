package com.prography.lighton.genre.domain.entity;

import com.prography.lighton.common.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE genre SET status = false WHERE id = ?")
@SQLRestriction("status = true")
public class Genre extends BaseEntity {

    private String name;
}
