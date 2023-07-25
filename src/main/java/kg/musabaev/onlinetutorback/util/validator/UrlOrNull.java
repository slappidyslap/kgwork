package kg.musabaev.onlinetutorback.util.validator;


import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.net.MalformedURLException;

/**
 * @see org.hibernate.validator.constraints.URL
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UrlOrNull.UrlOrNullValidator.class)
@Documented
public @interface UrlOrNull {
	String message() default "is not valid url";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String protocol() default "";

	String host() default "";

	int port() default -1;

	class UrlOrNullValidator implements ConstraintValidator<UrlOrNull, CharSequence> {

		private String protocol;
		private String host;
		private int port;

		@Override
		public void initialize(UrlOrNull url) {
			this.protocol = url.protocol();
			this.host = url.host();
			this.port = url.port();
		}

		@Override
		public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
			if (value == null) return true;
			if (value.length() != 0) {
				java.net.URL url;
				try {
					url = new java.net.URL(value.toString());
				} catch (MalformedURLException var5) {
					return false;
				}
				if (this.protocol != null && this.protocol.length() > 0 && !url.getProtocol().equals(this.protocol))
					return false;
				else if (this.host != null && this.host.length() > 0 && !url.getHost().equals(this.host))
					return false;
				else
					return this.port == -1 || url.getPort() == this.port;
			} else
				return true;
		}
	}
}