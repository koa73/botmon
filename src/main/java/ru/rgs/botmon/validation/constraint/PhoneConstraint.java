package ru.rgs.botmon.validation.constraint;


import ru.rgs.botmon.validation.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PhoneConstraint implements ConstraintValidator<Phone, String> {

    @Override
    public void initialize(Phone constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null ) {
            return false;
        }

        for (String phone : value.replaceAll("^,?","").split(",")) {
            if (!phone.matches("^((\\+7|8)[9]\\d{9})$"))
                return false;
        }
        return true;
    }
}
