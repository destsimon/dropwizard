package io.dropwizard.validation;

import io.dropwizard.util.SizeUnit;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be a {@link io.dropwizard.util.Size}
 * whose value must be higher or equal to the specified minimum.
 * <p/>
 * <code>null</code> elements are considered valid
 *
 * @deprecated Use {@link MinDataSize} for correct SI and IEC prefixes.
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Deprecated
@Documented
@Constraint(validatedBy = MinSizeValidator.class)
public @interface MinSize {
    String message() default "must be greater than or equal to {value} {unit}";

    Class<?>[] groups() default { };

    @SuppressWarnings("UnusedDeclaration") Class<? extends Payload>[] payload() default { };

    /**
     * @return value the element must be higher or equal to
     */
    long value();

    /**
     * @return unit of the value the element must be higher or equal to
     */
    SizeUnit unit() default SizeUnit.BYTES;
}
