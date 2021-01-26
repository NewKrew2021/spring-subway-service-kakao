package subway.path.domain;

import java.util.List;

public class Fare {
    private static final int BASIC_FARE = 1250;
    private static final int MIN_AGE_OF_CHILD = 6;
    private static final int MIN_AGE_OF_TEEN = 13;
    private static final int MIN_AGE_OF_ADULT = 19;

    public static int calculate(int distance, List<Integer> extraFareList) {
        return calculateByDistance(distance) + calculateExtraFareByLine(extraFareList);
    }

    private static int calculateByDistance(int distance) {
        return BASIC_FARE +
                (int)Math.ceil(Math.min(Math.max((distance - 10), 0), 40 ) / 5.0) * 100 +
                (int)Math.ceil(Math.max((distance - 50), 0) / 8.0) * 100;
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
