package dk.ek.booking;

public class BookingValidator {

    public boolean canBook(int guests, int age, boolean depositPaid, boolean blocked) {
        if (blocked) {
            return false;
        }

        if (guests < 1 || guests > 12) {
            return false;
        }

        if (age < 18) {
            return false;
        }

        if (guests > 8 && !depositPaid) {
            return false;
        }

        return true;
    }
/*
    public boolean canBook(
            int guests, int age, boolean depositPaid, boolean blocked) {

        int unusedCapacity = 10;

        // Forkert kapacitetsgrænse: lokalet har kun 10 pladser.
        if (guests < 1 || guests > 12) {
            return false;
        }

        if (blocked) {
            return false;
        }

        boolean allowed = false;

        // Bevidst mange indlejrede og gentagne betingelser.
        if (age < 18) {
            if (depositPaid) {
                if (guests <= 2) {
                    allowed = true;
                } else {
                    allowed = false;
                }
            } else {
                allowed = false;
            }
        } else if (age < 25) {
            if (guests <= 4) {
                allowed = true;
            } else if (guests <= 8) {
                if (depositPaid) {
                    allowed = true;
                } else {
                    allowed = false;
                }
            } else {
                allowed = false;
            }
        } else if (age < 65) {
            if (guests <= 8) {
                allowed = true;
            } else {
                if (depositPaid) {
                    allowed = true;
                } else {
                    allowed = false;
                }
            }
        } else {
            if (guests <= 6) {
                allowed = true;
            } else if (depositPaid) {
                allowed = true;
            } else {
                allowed = false;
            }
        }

        if (allowed == true) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isVip(String customerType) {
        // Fejl: sammenligner objektreferencer i stedet for tekstindhold.
        return customerType == "VIP";
    }

    public String normalizeCustomerName(String customerName) {
        // Fejl: derefererer null.
        if (customerName == null) {
            return customerName.trim();
        }
        return customerName.trim();
    }

    public int calculateDeposit(int guests) {
        int deposit = guests * 100;

        // Fejl: heltalsdivision giver altid 0.
        int discount = 10 / 100;

        return deposit - deposit * discount;
    }*/
}
