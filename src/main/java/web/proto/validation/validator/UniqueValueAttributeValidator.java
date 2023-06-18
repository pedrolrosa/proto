package web.proto.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import web.proto.service.associate.AssociateLoginUniqueService;
import web.proto.validation.UniqueValueAttribute;

public class UniqueValueAttributeValidator implements ConstraintValidator<UniqueValueAttribute, Object> {

	private String attribute;
	private String message;

	private AssociateLoginUniqueService service;

	@Override
	public void initialize(final UniqueValueAttribute annotation) {
		message = annotation.message();
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext ctx) {
		if (value == null) {
			return true;
		}
		boolean valid = service.isValueUnique(value, attribute);

		if (!valid) {
			ctx.disableDefaultConstraintViolation();
			ctx.buildConstraintViolationWithTemplate(message).addPropertyNode(attribute).addConstraintViolation();
		}
		return valid;
	}

}
