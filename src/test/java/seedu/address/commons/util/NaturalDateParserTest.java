package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.util.NaturalDateParser.parse;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class NaturalDateParserTest {

    /** Some usual inputs */
    @Test
    void parse_smoketest() {
        assertEquals(
                LocalDateTime.of(2006, 1, 2, 15, 0),
                parse("2 Jan 2006 3pm")
        );
        assertEquals(
                LocalDateTime.of(2006, 1, 2, 15, 4, 5),
                parse("Jan 2 2006 15:04:05")
        );
    }

    /** Inputs that are not a point - implementations should satisfy these behavior */
    @Test
    void parse_nonPointInputs() {
        // Use the end of the day/week
        assertEquals(
                LocalDateTime.of(2006, 1, 2, 23, 59, 59),
                parse("2 Jan 2006")
        );
        assertEquals(
                LocalDateTime.of(2006, 2, 28, 23, 59, 59),
                parse("Feb 2006")
        );
    }

    /** null */
    @Test
    void parse_null_throwsException() {
        assertThrows(NullPointerException.class, () -> parse(null));
    }

    /** Not a date/time */
    @Test
    void parse_notADateTime_throwsException() {
        assertThrows(NaturalDateParser.DateTimeNotFoundException.class, () -> parse("Hello World :p"));
    }
}
