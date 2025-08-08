package com.prography.lighton.member.common.domain.entity.vo;

import static io.micrometer.common.util.StringUtils.isBlank;

import com.prography.lighton.member.common.domain.exception.InvalidMemberException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Phone {

    private static final String PHONE_NUMBER_PATTERN = "^\\d{3}\\d{3,4}\\d{4}$";
    private static final String MASKED_PHONE_SUFFIX = "_DELETED_";

    @Column(nullable = false, length = 30, unique = true, name = "phone")
    private String value;

    public static Phone of(String phoneNumber) {
        if (isBlank(phoneNumber) || !phoneNumber.matches(PHONE_NUMBER_PATTERN)) {
            throw new InvalidMemberException("전화번호 형식이 올바르지 않습니다.");
        }
        return new Phone(phoneNumber);
    }

    public Phone withdrawMasked(Long memberId) {
        return new Phone(this.value + MASKED_PHONE_SUFFIX + memberId);
    }
}
