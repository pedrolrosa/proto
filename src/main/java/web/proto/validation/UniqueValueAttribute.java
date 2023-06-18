package web.proto.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import web.proto.validation.service.UniqueValue;
import web.proto.validation.validator.UniqueValueAttributeValidator;



@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValueAttributeValidator.class)
@Documented
public @interface UniqueValueAttribute {

    String attribute();

	String message() default "";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
	Class<? extends UniqueValue> service();
	
    String serviceQualifier() default "";
	
	@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		UniqueValueAttribute[] value();
	}
}
