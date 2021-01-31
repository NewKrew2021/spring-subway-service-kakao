package subway.path.domain;

import java.util.*;
import java.util.stream.Collectors;

public class Fare {
    private int fare;

    public Fare(int distance, List<Integer> extraFareList, Integer age){
        fare = FareCalculator.calculate(distance, extraFareList, age);
    }

    public int getFare() {
        return fare;
    }
}
