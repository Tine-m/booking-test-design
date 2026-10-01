package dk.ek.booking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeValidatorTest {

    @Test
    void parseTimeValue() {
        assertEquals(0, TimeValidator.parseTimeValue("2020-10-20"));
    }
}