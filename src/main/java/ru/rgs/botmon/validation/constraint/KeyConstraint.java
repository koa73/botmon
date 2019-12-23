package ru.rgs.botmon.validation.constraint;


import org.springframework.beans.factory.annotation.Value;
import ru.rgs.botmon.validation.Key;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class KeyConstraint implements ConstraintValidator<Key, String> {

    @Value("${bot.key}")
    private String key;

    @Override
    public void initialize(Key constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) {
            return true;
        }
        return value.matches(key);
    }
}
