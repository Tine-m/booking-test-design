package dk.ek.booking;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Den konsoliderede testsuite.
 *
 * De tre testdesignteknikker bruges først til at finde relevante test cases.
 * Derefter samles de, og identiske/overflødige cases kan fjernes.
 *
 * Bemærk: Målet er ikke nødvendigvis det absolut mindste antal tests.
 * Tests skal også være forståelige og dokumentere vigtige forretningsregler.
 */
class BookingValidatorConsolidatedTest {

    private final BookingValidator validator = new BookingValidator();

    @ParameterizedTest(name = "[{index}] {5}")
    @CsvFileSource(
            resources = "/consolidated-test-cases.csv",
            numLinesToSkip = 1
    )
    void bookingRules(
            int guests,
            int age,
            boolean depositPaid,
            boolean blocked,
            boolean expected,
            String reason) {

        assertEquals(expected,
                validator.canBook(guests, age, depositPaid, blocked));
    }
}
