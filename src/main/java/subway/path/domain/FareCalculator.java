package subway.path.domain;

public class FareCalculator {
    private static final int BASIC_FARE = 1250;

    private FareCalculator() {
    }

    public static int getFare(int distance, int extraFare) {
        return calculateFareByDistance(distance) + extraFare;
    }

    private static int calculateFareByDistance(int distance) {
        int countOverThan50 = (int) Math.ceil(Math.max(0, distance - 50) / 8.0);
        int countOverThan10 = Math.min((int) Math.ceil(Math.max(0, distance - 10) / 5.0), 8);
        return BASIC_FARE + (countOverThan50 + countOverThan10) * 100;
    }

    private static int getDiscountedFare(int fare, int age) {
//        if (age < 6) {
//
//        }
        if (age < 13) {
            return fare - (int) ((fare - 350) * 0.5);
        }
        if (age < 19) {
            return fare - (int) ((fare - 350) * 0.2);
        }
        return fare;
    }
}
