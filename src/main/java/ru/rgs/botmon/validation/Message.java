package ru.rgs.botmon.validation;

import ru.rgs.botmon.validation.constraint.MessageConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MessageConstraint.class)
@Target( { ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)

public @interface Message {

    String message() default "message must be less then {max}";

    int max();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target( { ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface  List {
        Message[] value();
    }
}