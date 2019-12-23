package ru.rgs.botmon.validation.constraint;


import ru.rgs.botmon.validation.Message;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class MessageConstraint implements ConstraintValidator<Message, String> {

    private int max;

    @Override
    public void initialize(Message constraintAnnotation) {
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null ) {
            return false;
        }
        return value.length() <= max;
    }
}
