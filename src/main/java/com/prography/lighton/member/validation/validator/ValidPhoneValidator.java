package com.prography.lighton.member.validation.validator;

import java.util.regex.Pattern;

import com.prography.lighton.member.validation.annotation.ValidPhone;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPhoneValidator implements ConstraintValidator<ValidPhone, String> {

	private static final Pattern PHONE_PATTERN = Pattern.compile("^01[0-9]{8,9}$");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && PHONE_PATTERN.matcher(value).matches();
	}
}
