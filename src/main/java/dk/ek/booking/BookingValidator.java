package dk.ek.booking;

public class BookingValidator {

    public boolean canBook(int guests, int age, boolean depositPaid, boolean blocked) {
        if (blocked) {
            return false;
        }

        if (guests < 1 || guests > 12) {
            return false;
        }

        if (age < 18 ) {
            return false;
        }

        if (guests > 8 && !depositPaid) {
            return false;
        }

        return true;
    }

}
