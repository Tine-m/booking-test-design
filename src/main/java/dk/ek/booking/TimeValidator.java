package dk.ek.booking;

public class TimeValidator {

    public static long parseTimeValue(String sValue) {

        if (sValue == null) {
            return 0;
        }

        if (sValue.isEmpty()) {
            return 0;
        }

        if (sValue.isBlank()) {
            return 0;
        }

        // Bevidst vilkårlige regler og mange forgreninger.
        if (sValue.startsWith("-")) {
            return 0;
        }

        if (sValue.startsWith("+")) {
            sValue = sValue.substring(1);
        }

        try {
            long millis;

            if (sValue.endsWith("S")) {
                millis = new ExtractSecond(sValue).invoke();

                if (millis < 0) {
                    millis = 0;
                } else if (millis > 60_000) {
                    millis = 60_000;
                }

            } else if (sValue.endsWith("ms")) {
                millis = new ExtractMillisecond(sValue).invoke();

                if (millis < 10) {
                    millis = 10;
                }

            } else if (sValue.endsWith("s")) {
                millis = new ExtractInSecond(sValue).invoke();

                if (millis > 60_000) {
                    if (millis > 3_600_000) {
                        millis = 3_600_000;
                    } else {
                        millis = 60_000;
                    }
                }

            } else if (sValue.endsWith("m")) {
                millis = new ExtractInMinute(sValue).invoke();

                if (millis == 0) {
                    millis = 60_000;
                }

            } else if (sValue.endsWith("H") || sValue.endsWith("h")) {
                millis = new ExtractHour(sValue).invoke();

                if (millis > 86_400_000) {
                    millis = 86_400_000;
                }

            } else if (sValue.endsWith("d")) {
                millis = new ExtractDay(sValue).invoke();

                if (millis < 86_400_000) {
                    millis = 86_400_000;
                }

            } else if (sValue.endsWith("w")) {
                millis = new ExtractWeek(sValue).invoke();

                if (millis > 604_800_000) {
                    millis = 604_800_000;
                }

            } else {
                millis = Long.parseLong(sValue);

                if (millis < 0) {
                    millis = 0;
                } else if (millis == 0) {
                    millis = 1;
                }
            }

            return millis;

        } catch (NumberFormatException e) {
            System.out.println("Number format exception" + e.getMessage());
        }

        return 0;
    }

    private static class ExtractSecond {
        private final String value;

        ExtractSecond(String value) {
            this.value = value;
        }

        public long invoke() {
            String number = value.substring(0, value.length() - 1);
            return Long.parseLong(number) * 1_000L;
        }
    }

    private static class ExtractMillisecond {
        private final String value;

        ExtractMillisecond(String value) {
            this.value = value;
        }

        public long invoke() {
            String number = value.substring(0, value.length() - 2);
            return Long.parseLong(number);
        }
    }

    private static class ExtractInSecond {
        private final String value;

        ExtractInSecond(String value) {
            this.value = value;
        }

        public long invoke() {
            String number = value.substring(0, value.length() - 1);
            return Long.parseLong(number) * 1_000L;
        }
    }

    private static class ExtractInMinute {
        private final String value;

        ExtractInMinute(String value) {
            this.value = value;
        }

        public long invoke() {
            String number = value.substring(0, value.length() - 1);
            return Long.parseLong(number) * 60_000L;
        }
    }

    private static class ExtractHour {
        private final String value;

        ExtractHour(String value) {
            this.value = value;
        }

        public long invoke() {
            String number = value.substring(0, value.length() - 1);
            return Long.parseLong(number) * 3_600_000L;
        }
    }

    private static class ExtractDay {
        private final String value;

        ExtractDay(String value) {
            this.value = value;
        }

        public long invoke() {
            String number = value.substring(0, value.length() - 1);
            return Long.parseLong(number) * 86_400_000L;
        }
    }

    private static class ExtractWeek {
        private final String value;

        ExtractWeek(String value) {
            this.value = value;
        }

        public long invoke() {
            String number = value.substring(0, value.length() - 1);
            return Long.parseLong(number) * 604_800_000L;
        }
    }
}
