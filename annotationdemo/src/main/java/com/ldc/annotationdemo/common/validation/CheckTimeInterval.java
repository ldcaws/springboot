package com.ldc.annotationdemo.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckTimeIntervalValidator.class)
@Documented
@Repeatable(CheckTimeInterval.List.class)
public @interface CheckTimeInterval {

    String startTime() default "from";
    String endTime() default "to";
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default { };


    @Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented@interface  List{
        CheckTimeInterval[] value();
    }
}