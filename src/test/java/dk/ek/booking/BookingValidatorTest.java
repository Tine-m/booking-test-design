package dk.ek.booking;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingValidatorTest {

    private final BookingValidator validator = new BookingValidator();

    /*
     * 1. ÆKVIVALENSANALYSE
     *
     * Antal gæster opdeles i tre klasser:
     *   < 1      ugyldig
     *   1..12    gyldig (andre regler kan stadig afvise bookingen)
     *   > 12     ugyldig
     *
     * Alder opdeles i:
     *   < 18     ugyldig
     *   >= 18    gyldig
     *
     * Testdata ligger i equivalence-partitioning.csv.
     */
    @ParameterizedTest(name = "[{index}] guests={0}, age={1} -> {4}")
    @CsvFileSource(
            resources = "/equivalence-partitioning.csv",
            numLinesToSkip = 1
    )
    void equivalencePartitioning(
            int guests,
            int age,
            boolean depositPaid,
            boolean blocked,
            boolean expected) {

        assertEquals(expected,
                validator.canBook(guests, age, depositPaid, blocked));
    }

    /*
     * 2. BOUNDARY VALUE ANALYSIS
     *
     * Grænser for antal gæster: 1 og 12.
     * Desuden er 8/9 interessant, fordi depositumskravet skifter her.
     * Grænse for alder: 18.
     *
     * Vi tester værdier på og omkring disse grænser.
     */
    @ParameterizedTest(name = "[{index}] guests={0}, age={1}, deposit={2} -> {4}")
    @CsvFileSource(
            resources = "/boundary-values.csv",
            numLinesToSkip = 1
    )
    void boundaryValueAnalysis(
            int guests,
            int age,
            boolean depositPaid,
            boolean blocked,
            boolean expected) {

        assertEquals(expected,
                validator.canBook(guests, age, depositPaid, blocked));
    }

    /*
     * 3. DECISION TABLE
     *
     * Kombinationen af:
     * - blokeret kunde
     * - gruppestørrelse
     * - depositum
     *
     * afgør om bookingen accepteres.
     *
     * Rækkerne i decision-table.csv repræsenterer reglerne
     * fra beslutningstabellen i README.md.
     */
    @ParameterizedTest(name = "[{index}] guests={0}, deposit={2}, blocked={3} -> {4}")
    @CsvFileSource(
            resources = "/decision-table.csv",
            numLinesToSkip = 1
    )
    void decisionTable(
            int guests,
            int age,
            boolean depositPaid,
            boolean blocked,
            boolean expected) {

        assertEquals(expected,
                validator.canBook(guests, age, depositPaid, blocked));
    }
}
