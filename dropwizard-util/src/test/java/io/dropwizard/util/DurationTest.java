package io.dropwizard.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class DurationTest {
    @Test
    void convertsDays() throws Exception {
        assertThat(Duration.days(2).toDays())
            .isEqualTo(2);
        assertThat(Duration.days(2).toHours())
            .isEqualTo(48);
    }

    @Test
    void convertsHours() throws Exception {
        assertThat(Duration.hours(2).toMinutes())
            .isEqualTo(120);
    }

    @Test
    void convertsMinutes() throws Exception {
        assertThat(Duration.minutes(3).toSeconds())
            .isEqualTo(180);
    }

    @Test
    void convertsSeconds() throws Exception {
        assertThat(Duration.seconds(2).toMilliseconds())
            .isEqualTo(2000);
    }

    @Test
    void convertsMilliseconds() throws Exception {
        assertThat(Duration.milliseconds(2).toMicroseconds())
            .isEqualTo(2000);
    }

    @Test
    void convertsMicroseconds() throws Exception {
        assertThat(Duration.microseconds(2).toNanoseconds())
            .isEqualTo(2000);
    }

    @Test
    void convertsNanoseconds() throws Exception {
        assertThat(Duration.nanoseconds(2).toNanoseconds())
            .isEqualTo(2);
    }

    @Test
    void parsesDays() throws Exception {
        assertThat(Duration.parse("1d"))
            .isEqualTo(Duration.days(1));

        assertThat(Duration.parse("1 day"))
            .isEqualTo(Duration.days(1));

        assertThat(Duration.parse("2 days"))
            .isEqualTo(Duration.days(2));
    }

    @Test
    void parsesHours() throws Exception {
        assertThat(Duration.parse("1h"))
            .isEqualTo(Duration.hours(1));

        assertThat(Duration.parse("1 hour"))
            .isEqualTo(Duration.hours(1));

        assertThat(Duration.parse("2 hours"))
            .isEqualTo(Duration.hours(2));
    }

    @Test
    void parsesMinutes() throws Exception {
        assertThat(Duration.parse("1m"))
            .isEqualTo(Duration.minutes(1));

        assertThat(Duration.parse("1min"))
            .isEqualTo(Duration.minutes(1));

        assertThat(Duration.parse("2mins"))
            .isEqualTo(Duration.minutes(2));

        assertThat(Duration.parse("1 minute"))
            .isEqualTo(Duration.minutes(1));

        assertThat(Duration.parse("2 minutes"))
            .isEqualTo(Duration.minutes(2));
    }

    @Test
    void parsesSeconds() throws Exception {
        assertThat(Duration.parse("1s"))
            .isEqualTo(Duration.seconds(1));

        assertThat(Duration.parse("1 second"))
            .isEqualTo(Duration.seconds(1));

        assertThat(Duration.parse("2 seconds"))
            .isEqualTo(Duration.seconds(2));
    }

    @Test
    void parsesMilliseconds() throws Exception {
        assertThat(Duration.parse("1ms"))
            .isEqualTo(Duration.milliseconds(1));

        assertThat(Duration.parse("1 millisecond"))
            .isEqualTo(Duration.milliseconds(1));

        assertThat(Duration.parse("2 milliseconds"))
            .isEqualTo(Duration.milliseconds(2));
    }

    @Test
    void parsesMicroseconds() throws Exception {
        assertThat(Duration.parse("1us"))
            .isEqualTo(Duration.microseconds(1));

        assertThat(Duration.parse("1 microsecond"))
            .isEqualTo(Duration.microseconds(1));

        assertThat(Duration.parse("2 microseconds"))
            .isEqualTo(Duration.microseconds(2));
    }

    @Test
    void parsesNanoseconds() throws Exception {
        assertThat(Duration.parse("1ns"))
            .isEqualTo(Duration.nanoseconds(1));

        assertThat(Duration.parse("1 nanosecond"))
            .isEqualTo(Duration.nanoseconds(1));

        assertThat(Duration.parse("2 nanoseconds"))
            .isEqualTo(Duration.nanoseconds(2));
    }

    @Test
    void parseDurationWithWhiteSpaces() {
        assertThat(Duration.parse("5   seconds"))
            .isEqualTo(Duration.seconds(5));
    }

    @Test
    void unableParseWrongDurationCount() {
        assertThatIllegalArgumentException().isThrownBy(() -> Duration.parse("five seconds"));
    }

    @Test
    void unableParseWrongDurationTimeUnit() {
        assertThatIllegalArgumentException().isThrownBy(() -> Duration.parse("1gs"));
    }

    @Test
    void unableParseWrongDurationFormat() {
        assertThatIllegalArgumentException().isThrownBy(() -> Duration.parse("1 milli second"));
    }

    @Test
    void isHumanReadable() throws Exception {
        assertThat(Duration.microseconds(1))
            .hasToString("1 microsecond");

        assertThat(Duration.microseconds(3))
            .hasToString("3 microseconds");
    }

    @Test
    void hasAQuantity() throws Exception {
        assertThat(Duration.microseconds(12).getQuantity())
            .isEqualTo(12);
    }

    @Test
    void hasAUnit() throws Exception {
        assertThat(Duration.microseconds(1).getUnit())
            .isEqualTo(TimeUnit.MICROSECONDS);
    }

    @Test
    void isComparable() throws Exception {
        // both zero
        assertThat(Duration.nanoseconds(0)).isEqualByComparingTo(Duration.nanoseconds(0));
        assertThat(Duration.nanoseconds(0)).isEqualByComparingTo(Duration.microseconds(0));
        assertThat(Duration.nanoseconds(0)).isEqualByComparingTo(Duration.milliseconds(0));
        assertThat(Duration.nanoseconds(0)).isEqualByComparingTo(Duration.seconds(0));
        assertThat(Duration.nanoseconds(0)).isEqualByComparingTo(Duration.minutes(0));
        assertThat(Duration.nanoseconds(0)).isEqualByComparingTo(Duration.hours(0));
        assertThat(Duration.nanoseconds(0)).isEqualByComparingTo(Duration.days(0));

        assertThat(Duration.microseconds(0)).isEqualByComparingTo(Duration.nanoseconds(0));
        assertThat(Duration.microseconds(0)).isEqualByComparingTo(Duration.microseconds(0));
        assertThat(Duration.microseconds(0)).isEqualByComparingTo(Duration.milliseconds(0));
        assertThat(Duration.microseconds(0)).isEqualByComparingTo(Duration.seconds(0));
        assertThat(Duration.microseconds(0)).isEqualByComparingTo(Duration.minutes(0));
        assertThat(Duration.microseconds(0)).isEqualByComparingTo(Duration.hours(0));
        assertThat(Duration.microseconds(0)).isEqualByComparingTo(Duration.days(0));

        assertThat(Duration.milliseconds(0)).isEqualByComparingTo(Duration.nanoseconds(0));
        assertThat(Duration.milliseconds(0)).isEqualByComparingTo(Duration.microseconds(0));
        assertThat(Duration.milliseconds(0)).isEqualByComparingTo(Duration.milliseconds(0));
        assertThat(Duration.milliseconds(0)).isEqualByComparingTo(Duration.seconds(0));
        assertThat(Duration.milliseconds(0)).isEqualByComparingTo(Duration.minutes(0));
        assertThat(Duration.milliseconds(0)).isEqualByComparingTo(Duration.hours(0));
        assertThat(Duration.milliseconds(0)).isEqualByComparingTo(Duration.days(0));

        assertThat(Duration.seconds(0)).isEqualByComparingTo(Duration.nanoseconds(0));
        assertThat(Duration.seconds(0)).isEqualByComparingTo(Duration.microseconds(0));
        assertThat(Duration.seconds(0)).isEqualByComparingTo(Duration.milliseconds(0));
        assertThat(Duration.seconds(0)).isEqualByComparingTo(Duration.seconds(0));
        assertThat(Duration.seconds(0)).isEqualByComparingTo(Duration.minutes(0));
        assertThat(Duration.seconds(0)).isEqualByComparingTo(Duration.hours(0));
        assertThat(Duration.seconds(0)).isEqualByComparingTo(Duration.days(0));

        assertThat(Duration.minutes(0)).isEqualByComparingTo(Duration.nanoseconds(0));
        assertThat(Duration.minutes(0)).isEqualByComparingTo(Duration.microseconds(0));
        assertThat(Duration.minutes(0)).isEqualByComparingTo(Duration.milliseconds(0));
        assertThat(Duration.minutes(0)).isEqualByComparingTo(Duration.seconds(0));
        assertThat(Duration.minutes(0)).isEqualByComparingTo(Duration.minutes(0));
        assertThat(Duration.minutes(0)).isEqualByComparingTo(Duration.hours(0));
        assertThat(Duration.minutes(0)).isEqualByComparingTo(Duration.days(0));

        assertThat(Duration.hours(0)).isEqualByComparingTo(Duration.nanoseconds(0));
        assertThat(Duration.hours(0)).isEqualByComparingTo(Duration.microseconds(0));
        assertThat(Duration.hours(0)).isEqualByComparingTo(Duration.milliseconds(0));
        assertThat(Duration.hours(0)).isEqualByComparingTo(Duration.seconds(0));
        assertThat(Duration.hours(0)).isEqualByComparingTo(Duration.minutes(0));
        assertThat(Duration.hours(0)).isEqualByComparingTo(Duration.hours(0));
        assertThat(Duration.hours(0)).isEqualByComparingTo(Duration.days(0));

        assertThat(Duration.days(0)).isEqualByComparingTo(Duration.nanoseconds(0));
        assertThat(Duration.days(0)).isEqualByComparingTo(Duration.microseconds(0));
        assertThat(Duration.days(0)).isEqualByComparingTo(Duration.milliseconds(0));
        assertThat(Duration.days(0)).isEqualByComparingTo(Duration.seconds(0));
        assertThat(Duration.days(0)).isEqualByComparingTo(Duration.minutes(0));
        assertThat(Duration.days(0)).isEqualByComparingTo(Duration.hours(0));
        assertThat(Duration.days(0)).isEqualByComparingTo(Duration.days(0));

        // one zero, one negative
        assertThat(Duration.nanoseconds(0)).isGreaterThan(Duration.nanoseconds(-1));
        assertThat(Duration.nanoseconds(0)).isGreaterThan(Duration.microseconds(-1));
        assertThat(Duration.nanoseconds(0)).isGreaterThan(Duration.milliseconds(-1));
        assertThat(Duration.nanoseconds(0)).isGreaterThan(Duration.seconds(-1));
        assertThat(Duration.nanoseconds(0)).isGreaterThan(Duration.minutes(-1));
        assertThat(Duration.nanoseconds(0)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.nanoseconds(0)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.microseconds(0)).isGreaterThan(Duration.nanoseconds(-1));
        assertThat(Duration.microseconds(0)).isGreaterThan(Duration.microseconds(-1));
        assertThat(Duration.microseconds(0)).isGreaterThan(Duration.milliseconds(-1));
        assertThat(Duration.microseconds(0)).isGreaterThan(Duration.seconds(-1));
        assertThat(Duration.microseconds(0)).isGreaterThan(Duration.minutes(-1));
        assertThat(Duration.microseconds(0)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.microseconds(0)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.milliseconds(0)).isGreaterThan(Duration.nanoseconds(-1));
        assertThat(Duration.milliseconds(0)).isGreaterThan(Duration.microseconds(-1));
        assertThat(Duration.milliseconds(0)).isGreaterThan(Duration.milliseconds(-1));
        assertThat(Duration.milliseconds(0)).isGreaterThan(Duration.seconds(-1));
        assertThat(Duration.milliseconds(0)).isGreaterThan(Duration.minutes(-1));
        assertThat(Duration.milliseconds(0)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.milliseconds(0)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.seconds(0)).isGreaterThan(Duration.nanoseconds(-1));
        assertThat(Duration.seconds(0)).isGreaterThan(Duration.microseconds(-1));
        assertThat(Duration.seconds(0)).isGreaterThan(Duration.milliseconds(-1));
        assertThat(Duration.seconds(0)).isGreaterThan(Duration.seconds(-1));
        assertThat(Duration.seconds(0)).isGreaterThan(Duration.minutes(-1));
        assertThat(Duration.seconds(0)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.seconds(0)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.minutes(0)).isGreaterThan(Duration.nanoseconds(-1));
        assertThat(Duration.minutes(0)).isGreaterThan(Duration.microseconds(-1));
        assertThat(Duration.minutes(0)).isGreaterThan(Duration.milliseconds(-1));
        assertThat(Duration.minutes(0)).isGreaterThan(Duration.seconds(-1));
        assertThat(Duration.minutes(0)).isGreaterThan(Duration.minutes(-1));
        assertThat(Duration.minutes(0)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.minutes(0)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.hours(0)).isGreaterThan(Duration.nanoseconds(-1));
        assertThat(Duration.hours(0)).isGreaterThan(Duration.microseconds(-1));
        assertThat(Duration.hours(0)).isGreaterThan(Duration.milliseconds(-1));
        assertThat(Duration.hours(0)).isGreaterThan(Duration.seconds(-1));
        assertThat(Duration.hours(0)).isGreaterThan(Duration.minutes(-1));
        assertThat(Duration.hours(0)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.hours(0)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.days(0)).isGreaterThan(Duration.nanoseconds(-1));
        assertThat(Duration.days(0)).isGreaterThan(Duration.microseconds(-1));
        assertThat(Duration.days(0)).isGreaterThan(Duration.milliseconds(-1));
        assertThat(Duration.days(0)).isGreaterThan(Duration.seconds(-1));
        assertThat(Duration.days(0)).isGreaterThan(Duration.minutes(-1));
        assertThat(Duration.days(0)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.days(0)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.nanoseconds(-1)).isLessThan(Duration.nanoseconds(0));
        assertThat(Duration.nanoseconds(-1)).isLessThan(Duration.microseconds(0));
        assertThat(Duration.nanoseconds(-1)).isLessThan(Duration.milliseconds(0));
        assertThat(Duration.nanoseconds(-1)).isLessThan(Duration.seconds(0));
        assertThat(Duration.nanoseconds(-1)).isLessThan(Duration.minutes(0));
        assertThat(Duration.nanoseconds(-1)).isLessThan(Duration.hours(0));
        assertThat(Duration.nanoseconds(-1)).isLessThan(Duration.days(0));

        assertThat(Duration.microseconds(-1)).isLessThan(Duration.nanoseconds(0));
        assertThat(Duration.microseconds(-1)).isLessThan(Duration.microseconds(0));
        assertThat(Duration.microseconds(-1)).isLessThan(Duration.milliseconds(0));
        assertThat(Duration.microseconds(-1)).isLessThan(Duration.seconds(0));
        assertThat(Duration.microseconds(-1)).isLessThan(Duration.minutes(0));
        assertThat(Duration.microseconds(-1)).isLessThan(Duration.hours(0));
        assertThat(Duration.microseconds(-1)).isLessThan(Duration.days(0));

        assertThat(Duration.milliseconds(-1)).isLessThan(Duration.nanoseconds(0));
        assertThat(Duration.milliseconds(-1)).isLessThan(Duration.microseconds(0));
        assertThat(Duration.milliseconds(-1)).isLessThan(Duration.milliseconds(0));
        assertThat(Duration.milliseconds(-1)).isLessThan(Duration.seconds(0));
        assertThat(Duration.milliseconds(-1)).isLessThan(Duration.minutes(0));
        assertThat(Duration.milliseconds(-1)).isLessThan(Duration.hours(0));
        assertThat(Duration.milliseconds(-1)).isLessThan(Duration.days(0));

        assertThat(Duration.seconds(-1)).isLessThan(Duration.nanoseconds(0));
        assertThat(Duration.seconds(-1)).isLessThan(Duration.microseconds(0));
        assertThat(Duration.seconds(-1)).isLessThan(Duration.milliseconds(0));
        assertThat(Duration.seconds(-1)).isLessThan(Duration.seconds(0));
        assertThat(Duration.seconds(-1)).isLessThan(Duration.minutes(0));
        assertThat(Duration.seconds(-1)).isLessThan(Duration.hours(0));
        assertThat(Duration.seconds(-1)).isLessThan(Duration.days(0));

        assertThat(Duration.minutes(-1)).isLessThan(Duration.nanoseconds(0));
        assertThat(Duration.minutes(-1)).isLessThan(Duration.microseconds(0));
        assertThat(Duration.minutes(-1)).isLessThan(Duration.milliseconds(0));
        assertThat(Duration.minutes(-1)).isLessThan(Duration.seconds(0));
        assertThat(Duration.minutes(-1)).isLessThan(Duration.minutes(0));
        assertThat(Duration.minutes(-1)).isLessThan(Duration.hours(0));
        assertThat(Duration.minutes(-1)).isLessThan(Duration.days(0));

        assertThat(Duration.hours(-1)).isLessThan(Duration.nanoseconds(0));
        assertThat(Duration.hours(-1)).isLessThan(Duration.microseconds(0));
        assertThat(Duration.hours(-1)).isLessThan(Duration.milliseconds(0));
        assertThat(Duration.hours(-1)).isLessThan(Duration.seconds(0));
        assertThat(Duration.hours(-1)).isLessThan(Duration.minutes(0));
        assertThat(Duration.hours(-1)).isLessThan(Duration.hours(0));
        assertThat(Duration.hours(-1)).isLessThan(Duration.days(0));

        assertThat(Duration.days(-1)).isLessThan(Duration.nanoseconds(0));
        assertThat(Duration.days(-1)).isLessThan(Duration.microseconds(0));
        assertThat(Duration.days(-1)).isLessThan(Duration.milliseconds(0));
        assertThat(Duration.days(-1)).isLessThan(Duration.seconds(0));
        assertThat(Duration.days(-1)).isLessThan(Duration.minutes(0));
        assertThat(Duration.days(-1)).isLessThan(Duration.hours(0));
        assertThat(Duration.days(-1)).isLessThan(Duration.days(0));

        // one zero, one positive
        assertThat(Duration.nanoseconds(0)).isLessThan(Duration.nanoseconds(1));
        assertThat(Duration.nanoseconds(0)).isLessThan(Duration.microseconds(1));
        assertThat(Duration.nanoseconds(0)).isLessThan(Duration.milliseconds(1));
        assertThat(Duration.nanoseconds(0)).isLessThan(Duration.seconds(1));
        assertThat(Duration.nanoseconds(0)).isLessThan(Duration.minutes(1));
        assertThat(Duration.nanoseconds(0)).isLessThan(Duration.hours(1));
        assertThat(Duration.nanoseconds(0)).isLessThan(Duration.days(1));

        assertThat(Duration.microseconds(0)).isLessThan(Duration.nanoseconds(1));
        assertThat(Duration.microseconds(0)).isLessThan(Duration.microseconds(1));
        assertThat(Duration.microseconds(0)).isLessThan(Duration.milliseconds(1));
        assertThat(Duration.microseconds(0)).isLessThan(Duration.seconds(1));
        assertThat(Duration.microseconds(0)).isLessThan(Duration.minutes(1));
        assertThat(Duration.microseconds(0)).isLessThan(Duration.hours(1));
        assertThat(Duration.microseconds(0)).isLessThan(Duration.days(1));

        assertThat(Duration.milliseconds(0)).isLessThan(Duration.nanoseconds(1));
        assertThat(Duration.milliseconds(0)).isLessThan(Duration.microseconds(1));
        assertThat(Duration.milliseconds(0)).isLessThan(Duration.milliseconds(1));
        assertThat(Duration.milliseconds(0)).isLessThan(Duration.seconds(1));
        assertThat(Duration.milliseconds(0)).isLessThan(Duration.minutes(1));
        assertThat(Duration.milliseconds(0)).isLessThan(Duration.hours(1));
        assertThat(Duration.milliseconds(0)).isLessThan(Duration.days(1));

        assertThat(Duration.seconds(0)).isLessThan(Duration.nanoseconds(1));
        assertThat(Duration.seconds(0)).isLessThan(Duration.microseconds(1));
        assertThat(Duration.seconds(0)).isLessThan(Duration.milliseconds(1));
        assertThat(Duration.seconds(0)).isLessThan(Duration.seconds(1));
        assertThat(Duration.seconds(0)).isLessThan(Duration.minutes(1));
        assertThat(Duration.seconds(0)).isLessThan(Duration.hours(1));
        assertThat(Duration.seconds(0)).isLessThan(Duration.days(1));

        assertThat(Duration.minutes(0)).isLessThan(Duration.nanoseconds(1));
        assertThat(Duration.minutes(0)).isLessThan(Duration.microseconds(1));
        assertThat(Duration.minutes(0)).isLessThan(Duration.milliseconds(1));
        assertThat(Duration.minutes(0)).isLessThan(Duration.seconds(1));
        assertThat(Duration.minutes(0)).isLessThan(Duration.minutes(1));
        assertThat(Duration.minutes(0)).isLessThan(Duration.hours(1));
        assertThat(Duration.minutes(0)).isLessThan(Duration.days(1));

        assertThat(Duration.hours(0)).isLessThan(Duration.nanoseconds(1));
        assertThat(Duration.hours(0)).isLessThan(Duration.microseconds(1));
        assertThat(Duration.hours(0)).isLessThan(Duration.milliseconds(1));
        assertThat(Duration.hours(0)).isLessThan(Duration.seconds(1));
        assertThat(Duration.hours(0)).isLessThan(Duration.minutes(1));
        assertThat(Duration.hours(0)).isLessThan(Duration.hours(1));
        assertThat(Duration.hours(0)).isLessThan(Duration.days(1));

        assertThat(Duration.days(0)).isLessThan(Duration.nanoseconds(1));
        assertThat(Duration.days(0)).isLessThan(Duration.microseconds(1));
        assertThat(Duration.days(0)).isLessThan(Duration.milliseconds(1));
        assertThat(Duration.days(0)).isLessThan(Duration.seconds(1));
        assertThat(Duration.days(0)).isLessThan(Duration.minutes(1));
        assertThat(Duration.days(0)).isLessThan(Duration.hours(1));
        assertThat(Duration.days(0)).isLessThan(Duration.days(1));

        assertThat(Duration.nanoseconds(1)).isGreaterThan(Duration.nanoseconds(0));
        assertThat(Duration.nanoseconds(1)).isGreaterThan(Duration.microseconds(0));
        assertThat(Duration.nanoseconds(1)).isGreaterThan(Duration.milliseconds(0));
        assertThat(Duration.nanoseconds(1)).isGreaterThan(Duration.seconds(0));
        assertThat(Duration.nanoseconds(1)).isGreaterThan(Duration.minutes(0));
        assertThat(Duration.nanoseconds(1)).isGreaterThan(Duration.hours(0));
        assertThat(Duration.nanoseconds(1)).isGreaterThan(Duration.days(0));

        assertThat(Duration.microseconds(1)).isGreaterThan(Duration.nanoseconds(0));
        assertThat(Duration.microseconds(1)).isGreaterThan(Duration.microseconds(0));
        assertThat(Duration.microseconds(1)).isGreaterThan(Duration.milliseconds(0));
        assertThat(Duration.microseconds(1)).isGreaterThan(Duration.seconds(0));
        assertThat(Duration.microseconds(1)).isGreaterThan(Duration.minutes(0));
        assertThat(Duration.microseconds(1)).isGreaterThan(Duration.hours(0));
        assertThat(Duration.microseconds(1)).isGreaterThan(Duration.days(0));

        assertThat(Duration.milliseconds(1)).isGreaterThan(Duration.nanoseconds(0));
        assertThat(Duration.milliseconds(1)).isGreaterThan(Duration.microseconds(0));
        assertThat(Duration.milliseconds(1)).isGreaterThan(Duration.milliseconds(0));
        assertThat(Duration.milliseconds(1)).isGreaterThan(Duration.seconds(0));
        assertThat(Duration.milliseconds(1)).isGreaterThan(Duration.minutes(0));
        assertThat(Duration.milliseconds(1)).isGreaterThan(Duration.hours(0));
        assertThat(Duration.milliseconds(1)).isGreaterThan(Duration.days(0));

        assertThat(Duration.seconds(1)).isGreaterThan(Duration.nanoseconds(0));
        assertThat(Duration.seconds(1)).isGreaterThan(Duration.microseconds(0));
        assertThat(Duration.seconds(1)).isGreaterThan(Duration.milliseconds(0));
        assertThat(Duration.seconds(1)).isGreaterThan(Duration.seconds(0));
        assertThat(Duration.seconds(1)).isGreaterThan(Duration.minutes(0));
        assertThat(Duration.seconds(1)).isGreaterThan(Duration.hours(0));
        assertThat(Duration.seconds(1)).isGreaterThan(Duration.days(0));

        assertThat(Duration.minutes(1)).isGreaterThan(Duration.nanoseconds(0));
        assertThat(Duration.minutes(1)).isGreaterThan(Duration.microseconds(0));
        assertThat(Duration.minutes(1)).isGreaterThan(Duration.milliseconds(0));
        assertThat(Duration.minutes(1)).isGreaterThan(Duration.seconds(0));
        assertThat(Duration.minutes(1)).isGreaterThan(Duration.minutes(0));
        assertThat(Duration.minutes(1)).isGreaterThan(Duration.hours(0));
        assertThat(Duration.minutes(1)).isGreaterThan(Duration.days(0));

        assertThat(Duration.hours(1)).isGreaterThan(Duration.nanoseconds(0));
        assertThat(Duration.hours(1)).isGreaterThan(Duration.microseconds(0));
        assertThat(Duration.hours(1)).isGreaterThan(Duration.milliseconds(0));
        assertThat(Duration.hours(1)).isGreaterThan(Duration.seconds(0));
        assertThat(Duration.hours(1)).isGreaterThan(Duration.minutes(0));
        assertThat(Duration.hours(1)).isGreaterThan(Duration.hours(0));
        assertThat(Duration.hours(1)).isGreaterThan(Duration.days(0));

        assertThat(Duration.days(1)).isGreaterThan(Duration.nanoseconds(0));
        assertThat(Duration.days(1)).isGreaterThan(Duration.microseconds(0));
        assertThat(Duration.days(1)).isGreaterThan(Duration.milliseconds(0));
        assertThat(Duration.days(1)).isGreaterThan(Duration.seconds(0));
        assertThat(Duration.days(1)).isGreaterThan(Duration.minutes(0));
        assertThat(Duration.days(1)).isGreaterThan(Duration.hours(0));
        assertThat(Duration.days(1)).isGreaterThan(Duration.days(0));

        // both negative
        assertThat(Duration.nanoseconds(-2)).isLessThan(Duration.nanoseconds(-1));
        assertThat(Duration.nanoseconds(-2)).isGreaterThan(Duration.microseconds(-1));
        assertThat(Duration.nanoseconds(-2)).isGreaterThan(Duration.milliseconds(-1));
        assertThat(Duration.nanoseconds(-2)).isGreaterThan(Duration.seconds(-1));
        assertThat(Duration.nanoseconds(-2)).isGreaterThan(Duration.minutes(-1));
        assertThat(Duration.nanoseconds(-2)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.nanoseconds(-2)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.microseconds(-2)).isLessThan(Duration.nanoseconds(-1));
        assertThat(Duration.microseconds(-2)).isLessThan(Duration.microseconds(-1));
        assertThat(Duration.microseconds(-2)).isGreaterThan(Duration.milliseconds(-1));
        assertThat(Duration.microseconds(-2)).isGreaterThan(Duration.seconds(-1));
        assertThat(Duration.microseconds(-2)).isGreaterThan(Duration.minutes(-1));
        assertThat(Duration.microseconds(-2)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.microseconds(-2)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.milliseconds(-2)).isLessThan(Duration.nanoseconds(-1));
        assertThat(Duration.milliseconds(-2)).isLessThan(Duration.microseconds(-1));
        assertThat(Duration.milliseconds(-2)).isLessThan(Duration.milliseconds(-1));
        assertThat(Duration.milliseconds(-2)).isGreaterThan(Duration.seconds(-1));
        assertThat(Duration.milliseconds(-2)).isGreaterThan(Duration.minutes(-1));
        assertThat(Duration.milliseconds(-2)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.milliseconds(-2)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.seconds(-2)).isLessThan(Duration.nanoseconds(-1));
        assertThat(Duration.seconds(-2)).isLessThan(Duration.microseconds(-1));
        assertThat(Duration.seconds(-2)).isLessThan(Duration.milliseconds(-1));
        assertThat(Duration.seconds(-2)).isLessThan(Duration.seconds(-1));
        assertThat(Duration.seconds(-2)).isGreaterThan(Duration.minutes(-1));
        assertThat(Duration.seconds(-2)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.seconds(-2)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.minutes(-2)).isLessThan(Duration.nanoseconds(-1));
        assertThat(Duration.minutes(-2)).isLessThan(Duration.microseconds(-1));
        assertThat(Duration.minutes(-2)).isLessThan(Duration.milliseconds(-1));
        assertThat(Duration.minutes(-2)).isLessThan(Duration.seconds(-1));
        assertThat(Duration.minutes(-2)).isLessThan(Duration.minutes(-1));
        assertThat(Duration.minutes(-2)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.minutes(-2)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.hours(-2)).isLessThan(Duration.nanoseconds(-1));
        assertThat(Duration.hours(-2)).isLessThan(Duration.microseconds(-1));
        assertThat(Duration.hours(-2)).isLessThan(Duration.milliseconds(-1));
        assertThat(Duration.hours(-2)).isLessThan(Duration.seconds(-1));
        assertThat(Duration.hours(-2)).isLessThan(Duration.minutes(-1));
        assertThat(Duration.hours(-2)).isLessThan(Duration.hours(-1));
        assertThat(Duration.hours(-2)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.days(-2)).isLessThan(Duration.nanoseconds(-1));
        assertThat(Duration.days(-2)).isLessThan(Duration.microseconds(-1));
        assertThat(Duration.days(-2)).isLessThan(Duration.milliseconds(-1));
        assertThat(Duration.days(-2)).isLessThan(Duration.seconds(-1));
        assertThat(Duration.days(-2)).isLessThan(Duration.minutes(-1));
        assertThat(Duration.days(-2)).isLessThan(Duration.hours(-1));
        assertThat(Duration.days(-2)).isLessThan(Duration.days(-1));

        assertThat(Duration.nanoseconds(-1)).isGreaterThan(Duration.nanoseconds(-2));
        assertThat(Duration.nanoseconds(-1)).isGreaterThan(Duration.microseconds(-2));
        assertThat(Duration.nanoseconds(-1)).isGreaterThan(Duration.milliseconds(-2));
        assertThat(Duration.nanoseconds(-1)).isGreaterThan(Duration.seconds(-2));
        assertThat(Duration.nanoseconds(-1)).isGreaterThan(Duration.minutes(-2));
        assertThat(Duration.nanoseconds(-1)).isGreaterThan(Duration.hours(-2));
        assertThat(Duration.nanoseconds(-1)).isGreaterThan(Duration.days(-2));

        assertThat(Duration.microseconds(-1)).isLessThan(Duration.nanoseconds(-2));
        assertThat(Duration.microseconds(-1)).isGreaterThan(Duration.microseconds(-2));
        assertThat(Duration.microseconds(-1)).isGreaterThan(Duration.milliseconds(-2));
        assertThat(Duration.microseconds(-1)).isGreaterThan(Duration.seconds(-2));
        assertThat(Duration.microseconds(-1)).isGreaterThan(Duration.minutes(-2));
        assertThat(Duration.microseconds(-1)).isGreaterThan(Duration.hours(-2));
        assertThat(Duration.microseconds(-1)).isGreaterThan(Duration.days(-2));

        assertThat(Duration.milliseconds(-1)).isLessThan(Duration.nanoseconds(-2));
        assertThat(Duration.milliseconds(-1)).isLessThan(Duration.microseconds(-2));
        assertThat(Duration.milliseconds(-1)).isGreaterThan(Duration.milliseconds(-2));
        assertThat(Duration.milliseconds(-1)).isGreaterThan(Duration.seconds(-2));
        assertThat(Duration.milliseconds(-1)).isGreaterThan(Duration.minutes(-2));
        assertThat(Duration.milliseconds(-1)).isGreaterThan(Duration.hours(-2));
        assertThat(Duration.milliseconds(-1)).isGreaterThan(Duration.days(-2));

        assertThat(Duration.seconds(-1)).isLessThan(Duration.nanoseconds(-2));
        assertThat(Duration.seconds(-1)).isLessThan(Duration.microseconds(-2));
        assertThat(Duration.seconds(-1)).isLessThan(Duration.milliseconds(-2));
        assertThat(Duration.seconds(-1)).isGreaterThan(Duration.seconds(-2));
        assertThat(Duration.seconds(-1)).isGreaterThan(Duration.minutes(-2));
        assertThat(Duration.seconds(-1)).isGreaterThan(Duration.hours(-2));
        assertThat(Duration.seconds(-1)).isGreaterThan(Duration.days(-2));

        assertThat(Duration.minutes(-1)).isLessThan(Duration.nanoseconds(-2));
        assertThat(Duration.minutes(-1)).isLessThan(Duration.microseconds(-2));
        assertThat(Duration.minutes(-1)).isLessThan(Duration.milliseconds(-2));
        assertThat(Duration.minutes(-1)).isLessThan(Duration.seconds(-2));
        assertThat(Duration.minutes(-1)).isGreaterThan(Duration.minutes(-2));
        assertThat(Duration.minutes(-1)).isGreaterThan(Duration.hours(-2));
        assertThat(Duration.minutes(-1)).isGreaterThan(Duration.days(-2));

        assertThat(Duration.hours(-1)).isLessThan(Duration.nanoseconds(-2));
        assertThat(Duration.hours(-1)).isLessThan(Duration.microseconds(-2));
        assertThat(Duration.hours(-1)).isLessThan(Duration.milliseconds(-2));
        assertThat(Duration.hours(-1)).isLessThan(Duration.seconds(-2));
        assertThat(Duration.hours(-1)).isLessThan(Duration.minutes(-2));
        assertThat(Duration.hours(-1)).isGreaterThan(Duration.hours(-2));
        assertThat(Duration.hours(-1)).isGreaterThan(Duration.days(-2));

        assertThat(Duration.days(-1)).isLessThan(Duration.nanoseconds(-2));
        assertThat(Duration.days(-1)).isLessThan(Duration.microseconds(-2));
        assertThat(Duration.days(-1)).isLessThan(Duration.milliseconds(-2));
        assertThat(Duration.days(-1)).isLessThan(Duration.seconds(-2));
        assertThat(Duration.days(-1)).isLessThan(Duration.minutes(-2));
        assertThat(Duration.days(-1)).isLessThan(Duration.hours(-2));
        assertThat(Duration.days(-1)).isGreaterThan(Duration.days(-2));

        // both positive
        assertThat(Duration.nanoseconds(1)).isLessThan(Duration.nanoseconds(2));
        assertThat(Duration.nanoseconds(1)).isLessThan(Duration.microseconds(2));
        assertThat(Duration.nanoseconds(1)).isLessThan(Duration.milliseconds(2));
        assertThat(Duration.nanoseconds(1)).isLessThan(Duration.seconds(2));
        assertThat(Duration.nanoseconds(1)).isLessThan(Duration.minutes(2));
        assertThat(Duration.nanoseconds(1)).isLessThan(Duration.hours(2));
        assertThat(Duration.nanoseconds(1)).isLessThan(Duration.days(2));

        assertThat(Duration.microseconds(1)).isGreaterThan(Duration.nanoseconds(2));
        assertThat(Duration.microseconds(1)).isLessThan(Duration.microseconds(2));
        assertThat(Duration.microseconds(1)).isLessThan(Duration.milliseconds(2));
        assertThat(Duration.microseconds(1)).isLessThan(Duration.seconds(2));
        assertThat(Duration.microseconds(1)).isLessThan(Duration.minutes(2));
        assertThat(Duration.microseconds(1)).isLessThan(Duration.hours(2));
        assertThat(Duration.microseconds(1)).isLessThan(Duration.days(2));

        assertThat(Duration.milliseconds(1)).isGreaterThan(Duration.nanoseconds(2));
        assertThat(Duration.milliseconds(1)).isGreaterThan(Duration.microseconds(2));
        assertThat(Duration.milliseconds(1)).isLessThan(Duration.milliseconds(2));
        assertThat(Duration.milliseconds(1)).isLessThan(Duration.seconds(2));
        assertThat(Duration.milliseconds(1)).isLessThan(Duration.minutes(2));
        assertThat(Duration.milliseconds(1)).isLessThan(Duration.hours(2));
        assertThat(Duration.milliseconds(1)).isLessThan(Duration.days(2));

        assertThat(Duration.seconds(1)).isGreaterThan(Duration.nanoseconds(2));
        assertThat(Duration.seconds(1)).isGreaterThan(Duration.microseconds(2));
        assertThat(Duration.seconds(1)).isGreaterThan(Duration.milliseconds(2));
        assertThat(Duration.seconds(1)).isLessThan(Duration.seconds(2));
        assertThat(Duration.seconds(1)).isLessThan(Duration.minutes(2));
        assertThat(Duration.seconds(1)).isLessThan(Duration.hours(2));
        assertThat(Duration.seconds(1)).isLessThan(Duration.days(2));

        assertThat(Duration.minutes(1)).isGreaterThan(Duration.nanoseconds(2));
        assertThat(Duration.minutes(1)).isGreaterThan(Duration.microseconds(2));
        assertThat(Duration.minutes(1)).isGreaterThan(Duration.milliseconds(2));
        assertThat(Duration.minutes(1)).isGreaterThan(Duration.seconds(2));
        assertThat(Duration.minutes(1)).isLessThan(Duration.minutes(2));
        assertThat(Duration.minutes(1)).isLessThan(Duration.hours(2));
        assertThat(Duration.minutes(1)).isLessThan(Duration.days(2));

        assertThat(Duration.hours(1)).isGreaterThan(Duration.nanoseconds(2));
        assertThat(Duration.hours(1)).isGreaterThan(Duration.microseconds(2));
        assertThat(Duration.hours(1)).isGreaterThan(Duration.milliseconds(2));
        assertThat(Duration.hours(1)).isGreaterThan(Duration.seconds(2));
        assertThat(Duration.hours(1)).isGreaterThan(Duration.minutes(2));
        assertThat(Duration.hours(1)).isLessThan(Duration.hours(2));
        assertThat(Duration.hours(1)).isLessThan(Duration.days(2));

        assertThat(Duration.days(1)).isGreaterThan(Duration.nanoseconds(2));
        assertThat(Duration.days(1)).isGreaterThan(Duration.microseconds(2));
        assertThat(Duration.days(1)).isGreaterThan(Duration.milliseconds(2));
        assertThat(Duration.days(1)).isGreaterThan(Duration.seconds(2));
        assertThat(Duration.days(1)).isGreaterThan(Duration.minutes(2));
        assertThat(Duration.days(1)).isGreaterThan(Duration.hours(2));
        assertThat(Duration.days(1)).isLessThan(Duration.days(2));

        assertThat(Duration.nanoseconds(2)).isGreaterThan(Duration.nanoseconds(1));
        assertThat(Duration.nanoseconds(2)).isLessThan(Duration.microseconds(1));
        assertThat(Duration.nanoseconds(2)).isLessThan(Duration.milliseconds(1));
        assertThat(Duration.nanoseconds(2)).isLessThan(Duration.seconds(1));
        assertThat(Duration.nanoseconds(2)).isLessThan(Duration.minutes(1));
        assertThat(Duration.nanoseconds(2)).isLessThan(Duration.hours(1));
        assertThat(Duration.nanoseconds(2)).isLessThan(Duration.days(1));

        assertThat(Duration.microseconds(2)).isGreaterThan(Duration.nanoseconds(1));
        assertThat(Duration.microseconds(2)).isGreaterThan(Duration.microseconds(1));
        assertThat(Duration.microseconds(2)).isLessThan(Duration.milliseconds(1));
        assertThat(Duration.microseconds(2)).isLessThan(Duration.seconds(1));
        assertThat(Duration.microseconds(2)).isLessThan(Duration.minutes(1));
        assertThat(Duration.microseconds(2)).isLessThan(Duration.hours(1));
        assertThat(Duration.microseconds(2)).isLessThan(Duration.days(1));

        assertThat(Duration.milliseconds(2)).isGreaterThan(Duration.nanoseconds(1));
        assertThat(Duration.milliseconds(2)).isGreaterThan(Duration.microseconds(1));
        assertThat(Duration.milliseconds(2)).isGreaterThan(Duration.milliseconds(1));
        assertThat(Duration.milliseconds(2)).isLessThan(Duration.seconds(1));
        assertThat(Duration.milliseconds(2)).isLessThan(Duration.minutes(1));
        assertThat(Duration.milliseconds(2)).isLessThan(Duration.hours(1));
        assertThat(Duration.milliseconds(2)).isLessThan(Duration.days(1));

        assertThat(Duration.seconds(2)).isGreaterThan(Duration.nanoseconds(1));
        assertThat(Duration.seconds(2)).isGreaterThan(Duration.microseconds(1));
        assertThat(Duration.seconds(2)).isGreaterThan(Duration.milliseconds(1));
        assertThat(Duration.seconds(2)).isGreaterThan(Duration.seconds(1));
        assertThat(Duration.seconds(2)).isLessThan(Duration.minutes(1));
        assertThat(Duration.seconds(2)).isLessThan(Duration.hours(1));
        assertThat(Duration.seconds(2)).isLessThan(Duration.days(1));

        assertThat(Duration.minutes(2)).isGreaterThan(Duration.nanoseconds(1));
        assertThat(Duration.minutes(2)).isGreaterThan(Duration.microseconds(1));
        assertThat(Duration.minutes(2)).isGreaterThan(Duration.milliseconds(1));
        assertThat(Duration.minutes(2)).isGreaterThan(Duration.seconds(1));
        assertThat(Duration.minutes(2)).isGreaterThan(Duration.minutes(1));
        assertThat(Duration.minutes(2)).isLessThan(Duration.hours(1));
        assertThat(Duration.minutes(2)).isLessThan(Duration.days(1));

        assertThat(Duration.hours(2)).isGreaterThan(Duration.nanoseconds(1));
        assertThat(Duration.hours(2)).isGreaterThan(Duration.microseconds(1));
        assertThat(Duration.hours(2)).isGreaterThan(Duration.milliseconds(1));
        assertThat(Duration.hours(2)).isGreaterThan(Duration.seconds(1));
        assertThat(Duration.hours(2)).isGreaterThan(Duration.minutes(1));
        assertThat(Duration.hours(2)).isGreaterThan(Duration.hours(1));
        assertThat(Duration.hours(2)).isLessThan(Duration.days(1));

        assertThat(Duration.days(2)).isGreaterThan(Duration.nanoseconds(1));
        assertThat(Duration.days(2)).isGreaterThan(Duration.microseconds(1));
        assertThat(Duration.days(2)).isGreaterThan(Duration.milliseconds(1));
        assertThat(Duration.days(2)).isGreaterThan(Duration.seconds(1));
        assertThat(Duration.days(2)).isGreaterThan(Duration.minutes(1));
        assertThat(Duration.days(2)).isGreaterThan(Duration.hours(1));
        assertThat(Duration.days(2)).isGreaterThan(Duration.days(1));

        // one negative, one positive
        assertThat(Duration.nanoseconds(-1)).isLessThan(Duration.nanoseconds(1));
        assertThat(Duration.nanoseconds(-1)).isLessThan(Duration.microseconds(1));
        assertThat(Duration.nanoseconds(-1)).isLessThan(Duration.milliseconds(1));
        assertThat(Duration.nanoseconds(-1)).isLessThan(Duration.seconds(1));
        assertThat(Duration.nanoseconds(-1)).isLessThan(Duration.minutes(1));
        assertThat(Duration.nanoseconds(-1)).isLessThan(Duration.hours(1));
        assertThat(Duration.nanoseconds(-1)).isLessThan(Duration.days(1));

        assertThat(Duration.microseconds(-1)).isLessThan(Duration.nanoseconds(1));
        assertThat(Duration.microseconds(-1)).isLessThan(Duration.microseconds(1));
        assertThat(Duration.microseconds(-1)).isLessThan(Duration.milliseconds(1));
        assertThat(Duration.microseconds(-1)).isLessThan(Duration.seconds(1));
        assertThat(Duration.microseconds(-1)).isLessThan(Duration.minutes(1));
        assertThat(Duration.microseconds(-1)).isLessThan(Duration.hours(1));
        assertThat(Duration.microseconds(-1)).isLessThan(Duration.days(1));

        assertThat(Duration.milliseconds(-1)).isLessThan(Duration.nanoseconds(1));
        assertThat(Duration.milliseconds(-1)).isLessThan(Duration.microseconds(1));
        assertThat(Duration.milliseconds(-1)).isLessThan(Duration.milliseconds(1));
        assertThat(Duration.milliseconds(-1)).isLessThan(Duration.seconds(1));
        assertThat(Duration.milliseconds(-1)).isLessThan(Duration.minutes(1));
        assertThat(Duration.milliseconds(-1)).isLessThan(Duration.hours(1));
        assertThat(Duration.milliseconds(-1)).isLessThan(Duration.days(1));

        assertThat(Duration.seconds(-1)).isLessThan(Duration.nanoseconds(1));
        assertThat(Duration.seconds(-1)).isLessThan(Duration.microseconds(1));
        assertThat(Duration.seconds(-1)).isLessThan(Duration.milliseconds(1));
        assertThat(Duration.seconds(-1)).isLessThan(Duration.seconds(1));
        assertThat(Duration.seconds(-1)).isLessThan(Duration.minutes(1));
        assertThat(Duration.seconds(-1)).isLessThan(Duration.hours(1));
        assertThat(Duration.seconds(-1)).isLessThan(Duration.days(1));

        assertThat(Duration.minutes(-1)).isLessThan(Duration.nanoseconds(1));
        assertThat(Duration.minutes(-1)).isLessThan(Duration.microseconds(1));
        assertThat(Duration.minutes(-1)).isLessThan(Duration.milliseconds(1));
        assertThat(Duration.minutes(-1)).isLessThan(Duration.seconds(1));
        assertThat(Duration.minutes(-1)).isLessThan(Duration.minutes(1));
        assertThat(Duration.minutes(-1)).isLessThan(Duration.hours(1));
        assertThat(Duration.minutes(-1)).isLessThan(Duration.days(1));

        assertThat(Duration.hours(-1)).isLessThan(Duration.nanoseconds(1));
        assertThat(Duration.hours(-1)).isLessThan(Duration.microseconds(1));
        assertThat(Duration.hours(-1)).isLessThan(Duration.milliseconds(1));
        assertThat(Duration.hours(-1)).isLessThan(Duration.seconds(1));
        assertThat(Duration.hours(-1)).isLessThan(Duration.minutes(1));
        assertThat(Duration.hours(-1)).isLessThan(Duration.hours(1));
        assertThat(Duration.hours(-1)).isLessThan(Duration.days(1));

        assertThat(Duration.days(-1)).isLessThan(Duration.nanoseconds(1));
        assertThat(Duration.days(-1)).isLessThan(Duration.microseconds(1));
        assertThat(Duration.days(-1)).isLessThan(Duration.milliseconds(1));
        assertThat(Duration.days(-1)).isLessThan(Duration.seconds(1));
        assertThat(Duration.days(-1)).isLessThan(Duration.minutes(1));
        assertThat(Duration.days(-1)).isLessThan(Duration.hours(1));
        assertThat(Duration.days(-1)).isLessThan(Duration.days(1));

        assertThat(Duration.nanoseconds(1)).isGreaterThan(Duration.nanoseconds(-1));
        assertThat(Duration.nanoseconds(1)).isGreaterThan(Duration.microseconds(-1));
        assertThat(Duration.nanoseconds(1)).isGreaterThan(Duration.milliseconds(-1));
        assertThat(Duration.nanoseconds(1)).isGreaterThan(Duration.seconds(-1));
        assertThat(Duration.nanoseconds(1)).isGreaterThan(Duration.minutes(-1));
        assertThat(Duration.nanoseconds(1)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.nanoseconds(1)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.microseconds(1)).isGreaterThan(Duration.nanoseconds(-1));
        assertThat(Duration.microseconds(1)).isGreaterThan(Duration.microseconds(-1));
        assertThat(Duration.microseconds(1)).isGreaterThan(Duration.milliseconds(-1));
        assertThat(Duration.microseconds(1)).isGreaterThan(Duration.seconds(-1));
        assertThat(Duration.microseconds(1)).isGreaterThan(Duration.minutes(-1));
        assertThat(Duration.microseconds(1)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.microseconds(1)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.milliseconds(1)).isGreaterThan(Duration.nanoseconds(-1));
        assertThat(Duration.milliseconds(1)).isGreaterThan(Duration.microseconds(-1));
        assertThat(Duration.milliseconds(1)).isGreaterThan(Duration.milliseconds(-1));
        assertThat(Duration.milliseconds(1)).isGreaterThan(Duration.seconds(-1));
        assertThat(Duration.milliseconds(1)).isGreaterThan(Duration.minutes(-1));
        assertThat(Duration.milliseconds(1)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.milliseconds(1)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.seconds(1)).isGreaterThan(Duration.nanoseconds(-1));
        assertThat(Duration.seconds(1)).isGreaterThan(Duration.microseconds(-1));
        assertThat(Duration.seconds(1)).isGreaterThan(Duration.milliseconds(-1));
        assertThat(Duration.seconds(1)).isGreaterThan(Duration.seconds(-1));
        assertThat(Duration.seconds(1)).isGreaterThan(Duration.minutes(-1));
        assertThat(Duration.seconds(1)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.seconds(1)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.minutes(1)).isGreaterThan(Duration.nanoseconds(-1));
        assertThat(Duration.minutes(1)).isGreaterThan(Duration.microseconds(-1));
        assertThat(Duration.minutes(1)).isGreaterThan(Duration.milliseconds(-1));
        assertThat(Duration.minutes(1)).isGreaterThan(Duration.seconds(-1));
        assertThat(Duration.minutes(1)).isGreaterThan(Duration.minutes(-1));
        assertThat(Duration.minutes(1)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.minutes(1)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.hours(1)).isGreaterThan(Duration.nanoseconds(-1));
        assertThat(Duration.hours(1)).isGreaterThan(Duration.microseconds(-1));
        assertThat(Duration.hours(1)).isGreaterThan(Duration.milliseconds(-1));
        assertThat(Duration.hours(1)).isGreaterThan(Duration.seconds(-1));
        assertThat(Duration.hours(1)).isGreaterThan(Duration.minutes(-1));
        assertThat(Duration.hours(1)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.hours(1)).isGreaterThan(Duration.days(-1));

        assertThat(Duration.days(1)).isGreaterThan(Duration.nanoseconds(-1));
        assertThat(Duration.days(1)).isGreaterThan(Duration.microseconds(-1));
        assertThat(Duration.days(1)).isGreaterThan(Duration.milliseconds(-1));
        assertThat(Duration.days(1)).isGreaterThan(Duration.seconds(-1));
        assertThat(Duration.days(1)).isGreaterThan(Duration.minutes(-1));
        assertThat(Duration.days(1)).isGreaterThan(Duration.hours(-1));
        assertThat(Duration.days(1)).isGreaterThan(Duration.days(-1));
    }

    @Test
    void serializesCorrectlyWithJackson() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();

        assertThat(mapper.writeValueAsString(Duration.nanoseconds(0L))).isEqualTo("\"0 nanoseconds\"");
        assertThat(mapper.writeValueAsString(Duration.nanoseconds(1L))).isEqualTo("\"1 nanosecond\"");
        assertThat(mapper.writeValueAsString(Duration.nanoseconds(2L))).isEqualTo("\"2 nanoseconds\"");
        assertThat(mapper.writeValueAsString(Duration.microseconds(0L))).isEqualTo("\"0 microseconds\"");
        assertThat(mapper.writeValueAsString(Duration.microseconds(1L))).isEqualTo("\"1 microsecond\"");
        assertThat(mapper.writeValueAsString(Duration.microseconds(2L))).isEqualTo("\"2 microseconds\"");
        assertThat(mapper.writeValueAsString(Duration.milliseconds(0L))).isEqualTo("\"0 milliseconds\"");
        assertThat(mapper.writeValueAsString(Duration.milliseconds(1L))).isEqualTo("\"1 millisecond\"");
        assertThat(mapper.writeValueAsString(Duration.milliseconds(2L))).isEqualTo("\"2 milliseconds\"");
        assertThat(mapper.writeValueAsString(Duration.seconds(0L))).isEqualTo("\"0 seconds\"");
        assertThat(mapper.writeValueAsString(Duration.seconds(1L))).isEqualTo("\"1 second\"");
        assertThat(mapper.writeValueAsString(Duration.seconds(2L))).isEqualTo("\"2 seconds\"");
        assertThat(mapper.writeValueAsString(Duration.minutes(0L))).isEqualTo("\"0 minutes\"");
        assertThat(mapper.writeValueAsString(Duration.minutes(1L))).isEqualTo("\"1 minute\"");
        assertThat(mapper.writeValueAsString(Duration.minutes(2L))).isEqualTo("\"2 minutes\"");
        assertThat(mapper.writeValueAsString(Duration.hours(0L))).isEqualTo("\"0 hours\"");
        assertThat(mapper.writeValueAsString(Duration.hours(1L))).isEqualTo("\"1 hour\"");
        assertThat(mapper.writeValueAsString(Duration.hours(2L))).isEqualTo("\"2 hours\"");
        assertThat(mapper.writeValueAsString(Duration.days(0L))).isEqualTo("\"0 days\"");
        assertThat(mapper.writeValueAsString(Duration.days(1L))).isEqualTo("\"1 day\"");
        assertThat(mapper.writeValueAsString(Duration.days(2L))).isEqualTo("\"2 days\"");
    }

    @Test
    void deserializesCorrectlyWithJackson() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();

        assertThat(mapper.readValue("\"0 nanoseconds\"", Duration.class)).isEqualTo(Duration.nanoseconds(0L));
        assertThat(mapper.readValue("\"1 nanosecond\"", Duration.class)).isEqualTo(Duration.nanoseconds(1L));
        assertThat(mapper.readValue("\"2 nanoseconds\"", Duration.class)).isEqualTo(Duration.nanoseconds(2L));
        assertThat(mapper.readValue("\"0 microseconds\"", Duration.class)).isEqualTo(Duration.microseconds(0L));
        assertThat(mapper.readValue("\"1 microsecond\"", Duration.class)).isEqualTo(Duration.microseconds(1L));
        assertThat(mapper.readValue("\"2 microseconds\"", Duration.class)).isEqualTo(Duration.microseconds(2L));
        assertThat(mapper.readValue("\"0 milliseconds\"", Duration.class)).isEqualTo(Duration.milliseconds(0L));
        assertThat(mapper.readValue("\"1 millisecond\"", Duration.class)).isEqualTo(Duration.milliseconds(1L));
        assertThat(mapper.readValue("\"2 milliseconds\"", Duration.class)).isEqualTo(Duration.milliseconds(2L));
        assertThat(mapper.readValue("\"0 seconds\"", Duration.class)).isEqualTo(Duration.seconds(0L));
        assertThat(mapper.readValue("\"1 second\"", Duration.class)).isEqualTo(Duration.seconds(1L));
        assertThat(mapper.readValue("\"2 seconds\"", Duration.class)).isEqualTo(Duration.seconds(2L));
        assertThat(mapper.readValue("\"0 minutes\"", Duration.class)).isEqualTo(Duration.minutes(0L));
        assertThat(mapper.readValue("\"1 minutes\"", Duration.class)).isEqualTo(Duration.minutes(1L));
        assertThat(mapper.readValue("\"2 minutes\"", Duration.class)).isEqualTo(Duration.minutes(2L));
        assertThat(mapper.readValue("\"0 hours\"", Duration.class)).isEqualTo(Duration.hours(0L));
        assertThat(mapper.readValue("\"1 hours\"", Duration.class)).isEqualTo(Duration.hours(1L));
        assertThat(mapper.readValue("\"2 hours\"", Duration.class)).isEqualTo(Duration.hours(2L));
        assertThat(mapper.readValue("\"0 days\"", Duration.class)).isEqualTo(Duration.days(0L));
        assertThat(mapper.readValue("\"1 day\"", Duration.class)).isEqualTo(Duration.days(1L));
        assertThat(mapper.readValue("\"2 days\"", Duration.class)).isEqualTo(Duration.days(2L));
    }

    @Test
    void testSerialization() throws IOException, ClassNotFoundException {
        final Duration duration = Duration.minutes(42L);
        final byte[] bytes;
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             final ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(duration);
            bytes = outputStream.toByteArray();
        }

        try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
             final ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            @SuppressWarnings("BanSerializableRead")
            final Object o = objectInputStream.readObject();
            assertThat(o)
                    .isInstanceOf(Duration.class)
                    .isEqualTo(duration);
        }
    }
}
