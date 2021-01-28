package subway.path.domain;

import subway.member.domain.AGE;

import java.util.List;

public class Fare {
    private static final int BASIC_FARE = 1250;
    private static final int EXTRA_FARE_DISTANCE_FIRST = 10;
    private static final int EXTRA_FARE_DISTANCE_SECOND = 50;
    private static final int EXTRA_FARE_DISTANCE_FIRST_UNIT = 5;
    private static final int EXTRA_FARE_DISTANCE_SECOND_UNIT = 8;
    private static final int EXTRA_FARE_UNIT = 100;

    public static int calculateFare(int distance, List<Integer> lineExtraFareList, int age){
        return discount(getBasicFare(distance) + calculateExtraFare(lineExtraFareList) , age);
    }

    private static int getBasicFare(int distance) {
        int result = BASIC_FARE;

        if (distance > EXTRA_FARE_DISTANCE_FIRST && distance <= EXTRA_FARE_DISTANCE_SECOND) {
            result += (int) Math.ceil((double)(distance - EXTRA_FARE_DISTANCE_FIRST) / EXTRA_FARE_DISTANCE_FIRST_UNIT) * EXTRA_FARE_UNIT;
        }

        if (distance > EXTRA_FARE_DISTANCE_SECOND) {
            result += (int) Math.ceil((double)(distance - EXTRA_FARE_DISTANCE_SECOND) / EXTRA_FARE_DISTANCE_SECOND_UNIT) * EXTRA_FARE_UNIT
                    + ((EXTRA_FARE_DISTANCE_SECOND - EXTRA_FARE_DISTANCE_FIRST) / EXTRA_FARE_DISTANCE_FIRST_UNIT) * EXTRA_FARE_UNIT;
        }
        return result;
    }

    private static int calculateExtraFare(List<Integer> lineExtraFareList) {
        return lineExtraFareList.stream().max(Integer::compareTo).orElse(0);
    }

    private static int discount(int fare, int age){
        AGE ageStatus = AGE.getAgeStatus(age);
        return (int)((fare - ageStatus.getDeduction()) * ageStatus.getSaleRate()) + ageStatus.getDeduction();
    }
}
