package com.prography.lighton.performance.domain.entity.vo;

import com.prography.lighton.common.BaseEntity;
import com.prography.lighton.performance.domain.entity.enums.Seat;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import lombok.AllArgsConstructor;

@Embeddable
public class Payment {

    private String account;

    private String bank;

    private String accountHolder;

    private Integer fee;

    // todo 나중에 값 검증 로직 구현하기
}

