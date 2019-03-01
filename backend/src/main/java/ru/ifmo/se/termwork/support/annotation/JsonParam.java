package ru.ifmo.se.termwork.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonParam {

    /**
     * @return key for json parameter. Parameter can be primitive, wrapping of primitive or {@code String}.
     * If this key doesn't correspond to anything in a request body and {@code defaultValue()}
     * doesn't set, than annotated parameter would be set to default value. For instance,
     * if parameter is object, than it would be null. If parameter is {@code int}, than it would
     * be 0 and so on.
     */
    String value();

    String defaultValue() default "";
}
