package subway.path.domain;

import java.util.List;

public class FareCalculator {

    private static final int BASIC_FARE = 1250;
    private static final int FREE_DISTANCE_BOUND = 10;
    private static final int FARE_DISCOUNT_DISTANCE_BOUND = 50;

    private static final int KID_LOWER_BOUND = 6;
    private static final int KID_UPPER_BOUND = 13;
    private static final int TEEN_UPPER_BOUND = 19;

    public static int calculate(int distance, List<Integer> extraFareList, Integer age){
        int basicFare = getFareByDistance(distance);
        int extraFare = getExtraFareByLines(extraFareList);

        return age == null ? basicFare + extraFare : discount(basicFare + extraFare, age);
    }

    private static int getFareByDistance(int distance) {
        int result = BASIC_FARE;

        if (distance > FREE_DISTANCE_BOUND && distance <= FARE_DISCOUNT_DISTANCE_BOUND) {
            result += (int) Math.ceil((double)(distance - 10) / 5) * 100;
        }

        if (distance > FARE_DISCOUNT_DISTANCE_BOUND) {
            result += (int) Math.ceil((double)(distance - 50) / 8) * 100 + 800;
        }
        return result;
    }

    private static int getExtraFareByLines(List<Integer> extraFareList){
        return extraFareList.stream().max(Integer::compare).orElse(0);
    }

    private static int discount(int fare, Integer age){
        if(age < KID_LOWER_BOUND){
            fare = 0;
        }

        if(age >=KID_LOWER_BOUND && age < KID_UPPER_BOUND){
            fare = (int) ((fare - 350) * 0.5) + 350;
        }

        if(age >= KID_UPPER_BOUND && age < TEEN_UPPER_BOUND){
            fare = (int) ((fare - 350) * 0.8) + 350;
        }

        return fare;
    }
}
