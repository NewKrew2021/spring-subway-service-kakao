package subway.path.domain;

import subway.line.domain.Line;
import subway.path.dto.PathResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FareCalculator {

    private static final int BASIC_FARE = 1250;
    private static final int FREE_DISTANCE_BOUND = 10;
    private static final int FARE_DISCOUNT_DISTANCE_BOUND = 50;

    private static final int KID_LOWER_BOUND = 6;
    private static final int KID_UPPER_BOUND = 13;
    private static final int TEEN_UPPER_BOUND = 19;

    public static Fare calculate(PathResult result, Integer age){
        int basicFare = getFareByDistance(result.getDistance());

        List<Line> lines = new ArrayList<>();
        result.getPathVertices().getPathVertexList()
                .forEach(pathVertex -> lines.addAll(pathVertex.getLineList()));

        int extraFare = getExtraFareByLines(lines.stream()
                .map(Line::getExtraFare)
                .collect(Collectors.toList()));

        int fare = (age == null) ? basicFare + extraFare : discount(basicFare + extraFare, age);

        return new Fare(fare);
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
