package com.prography.lighton.member.validation.validator;

import java.util.regex.Pattern;

import com.prography.lighton.member.validation.annotation.ValidPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {
	private static final Pattern PASSWORD_PATTERN =
			Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=]).{8,}$");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && PASSWORD_PATTERN.matcher(value).matches();
	}
}
