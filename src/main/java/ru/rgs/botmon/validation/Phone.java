package ru.rgs.botmon.validation;

import ru.rgs.botmon.validation.constraint.PhoneConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneConstraint.class)
@Target( { ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)

public @interface Phone {
    String message() default "invalid phone format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target( { ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface  List {
        Phone[] value();
    }
}