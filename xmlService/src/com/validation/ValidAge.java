package com.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import javax.validation.Constraint;
import javax.validation.Payload;

/**

 * This is a custom annotation definition for the "age" field of the "StudentWrapper" bean

 *

 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StudentAgeValidator.class)
@Documented
public @interface ValidAge {

	String message() default "A student has to be at least 18 and at most 80";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
