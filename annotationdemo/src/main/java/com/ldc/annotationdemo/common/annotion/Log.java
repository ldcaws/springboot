package com.ldc.annotationdemo.common.annotion;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    String content() default "";

    String eventType() default "0";

    String eventLevel() default "0";

    String operationType() default "0";

}
