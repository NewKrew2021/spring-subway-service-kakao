package subway.path.domain;

import java.util.*;
import java.util.stream.Collectors;

public class Fare {
    private int fare;

    private final int BASIC_FARE = 1250;
    private final int FREE_DISTANCE_BOUND = 10;
    private final int FARE_DISCOUNT_DISTANCE_BOUND = 50;

    private final int KID_LOWER_BOUND = 6;
    private final int KID_UPPER_BOUND = 13;
    private final int TEEN_UPPER_BOUND = 19;

    public Fare(int distance, List<Integer> extraFareList){
        fare = calculateFare(distance, extraFareList);
    }

    public int calculateFare(int distance, List<Integer> extraFareList){
        int basicFare = getFareByDistance(distance);
        int extraFare = getExtraFareByLines(extraFareList);

        return basicFare + extraFare;
    }

    private int getFareByDistance(int distance) {
        int result = BASIC_FARE;

        if (distance > FREE_DISTANCE_BOUND && distance <= FARE_DISCOUNT_DISTANCE_BOUND) {
            result += (int) Math.ceil((double)(distance - 10) / 5) * 100;
        }

        if (distance > FARE_DISCOUNT_DISTANCE_BOUND) {
            result += (int) Math.ceil((double)(distance - 50) / 8) * 100 + 800;
        }
        return result;
    }

    private int getExtraFareByLines(List<Integer> extraFareList){
        return extraFareList.stream().max(Integer::compare).orElse(0);
    }

    public void discount(int age){
        if(age < KID_LOWER_BOUND){
            fare = 0;
        }

        if(age >=KID_LOWER_BOUND && age < KID_UPPER_BOUND){
            fare = (int) ((fare - 350) * 0.5) + 350;
        }

        if(age >= KID_UPPER_BOUND && age < TEEN_UPPER_BOUND){
            fare = (int) ((fare - 350) * 0.8) + 350;
        }
    }

    public int getFare() {
        return fare;
    }
}
