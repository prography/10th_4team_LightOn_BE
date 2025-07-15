package com.prography.lighton.member.common.domain.entity.vo;

import static io.micrometer.common.util.StringUtils.isBlank;

import com.prography.lighton.member.common.domain.exception.InvalidMemberException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;
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
public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private static final String MASKED_EMAIL_SUFFIX = "_DELETED_";

    @Column(nullable = false, name = "email")
    private String value;

    public static Email of(String value) {
        if (isBlank(value) || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new InvalidMemberException("이메일 형식이 올바르지 않습니다.");
        }
        return new Email(value);
    }

    public Email withdrawMasked(Long memberId) {
        return new Email(this.value + MASKED_EMAIL_SUFFIX + memberId);
    }

}
