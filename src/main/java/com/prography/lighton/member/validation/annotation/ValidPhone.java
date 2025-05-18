package com.prography.lighton.member.validation.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.prography.lighton.member.validation.validator.ValidPhoneValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = ValidPhoneValidator.class)
public @interface ValidPhone {
	String message() default "올바르지 않은 전화번호 형식입니다.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

