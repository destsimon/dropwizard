package io.dropwizard.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

class PortRangeValidatorTest {
    @SuppressWarnings("PublicField")
    public static class Example {
        @PortRange
        public int port = 8080;

        @PortRange(min = 10000, max = 15000)
        public int otherPort = 10001;

        @Valid
        List<@PortRange Integer> ports = Collections.emptyList();
    }


    private final Validator validator = BaseValidator.newValidator();
    private final Example example = new Example();

    @BeforeEach
    void setUp() throws Exception {
        assumeThat(Locale.getDefault().getLanguage()).isEqualTo("en");
    }

    @Test
    void acceptsNonPrivilegedPorts() throws Exception {
        example.port = 2048;

        assertThat(validator.validate(example))
                .isEmpty();
    }

    @Test
    void acceptsDynamicPorts() throws Exception {
        example.port = 0;

        assertThat(validator.validate(example))
                .isEmpty();
    }

    @Test
    void rejectsNegativePorts() throws Exception {
        example.port = -1;

        assertThat(ConstraintViolations.format(validator.validate(example)))
                .containsOnly("port must be between 1 and 65535");
    }

    @Test
    void allowsForCustomMinimumPorts() throws Exception {
        example.otherPort = 8080;

        assertThat(ConstraintViolations.format(validator.validate(example)))
                .containsOnly("otherPort must be between 10000 and 15000");
    }

    @Test
    void allowsForCustomMaximumPorts() throws Exception {
        example.otherPort = 16000;

        assertThat(ConstraintViolations.format(validator.validate(example)))
                .containsOnly("otherPort must be between 10000 and 15000");
    }

    @Test
    void rejectsInvalidPortsInList() {
        example.ports = Collections.singletonList(-1);
        assertThat(ConstraintViolations.format(validator.validate(example)))
            .containsOnly("ports[0].<list element> must be between 1 and 65535");
    }
}
