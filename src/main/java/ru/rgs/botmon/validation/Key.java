package ru.rgs.botmon.validation;


import ru.rgs.botmon.validation.constraint.KeyConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = KeyConstraint.class)
@Target( { ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)

public @interface Key {
    String message() default "Invalid key value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target( { ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface  List {
        Key[] value();
    }
}