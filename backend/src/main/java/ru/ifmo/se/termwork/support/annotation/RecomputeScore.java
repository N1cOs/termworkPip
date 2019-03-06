package ru.ifmo.se.termwork.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RecomputeScore {

    Type value() default Type.ALL;

    enum Type{
        EXAMS, ACHIEVEMENTS, ALL
    }
}
