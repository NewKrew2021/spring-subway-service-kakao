package subway.path.domain;

import java.util.List;

public class Fare {
    private static final int BASIC_FARE = 1250;
    private static final int FIRST_PART_LIMIT = 10;

    private static final int SECOND_PART_LIMIT = 50;
    private static final int SECOND_PART_UNIT_FARE = 100;
    private static final int SECOND_PART_UNIT_DISTANCE = 5;

    private static final int THIRD_PART_LIMIT = Integer.MAX_VALUE;
    private static final int THIRD_PART_UNIT_FARE = 100;
    private static final int THIRD_PART_UNIT_DISTANCE = 8;

    private static final int MIN_AGE_OF_CHILD = 6;
    private static final int MIN_AGE_OF_TEEN = 13;
    private static final int MIN_AGE_OF_ADULT = 19;

    public static int calculate(int distance, List<Integer> extraFareList) {
        return calculateByDistance(distance) + calculateExtraFareByLine(extraFareList);
    }

    private static int calculateByDistance(int distance) {
        return BASIC_FARE
                + getFareOfEachPartionWith(distance, FIRST_PART_LIMIT, SECOND_PART_LIMIT, SECOND_PART_UNIT_DISTANCE, SECOND_PART_UNIT_FARE)
                + getFareOfEachPartionWith(distance, SECOND_PART_LIMIT, THIRD_PART_LIMIT, THIRD_PART_UNIT_DISTANCE, THIRD_PART_UNIT_FARE);
    }

    private static int getFareOfEachPartionWith(int totalDistance, int previousPartLimit, int currentPartLimit, int currentPartUnitDistance, int currentPartUnitFare) {
        //get distance usage in partition
        int distanceUsage = Math.max(0, Math.min(totalDistance, currentPartLimit) - previousPartLimit);

        //get count of unit to be used in this partition
        int unitCount = (distanceUsage + currentPartUnitDistance - 1) / currentPartUnitDistance;

        //calculate fare for current partition
        return unitCount * currentPartUnitFare;
    }

    private static int calculateExtraFareByLine(List<Integer> extraFareList) {
        return extraFareList.stream().mapToInt(v -> v).max().orElse(0);
    }

    public static int discountByAge(int fare, int age) {
        if(age < MIN_AGE_OF_CHILD) {
            return 0;
        }

        if(age < MIN_AGE_OF_TEEN){
            return fare - (int)((fare - 350) * 0.5);
        }

        if(age < MIN_AGE_OF_ADULT){
            return fare - (int)((fare - 350) * 0.2);
        }

        return fare;
    }
}
