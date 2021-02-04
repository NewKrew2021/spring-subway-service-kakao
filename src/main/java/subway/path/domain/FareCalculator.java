package subway.path.domain;

import subway.line.domain.Line;
import subway.path.dto.Fare;
import subway.path.dto.PathResult;

import java.util.List;

public class FareCalculator {
    private DistanceFarePolicy distanceFarePolicy;
    private LineExtraFarePolicy lineExtraFarePolicy;
    private AgeDiscountFarePolicy fareDiscount;

    public FareCalculator (){
        distanceFarePolicy = new DistanceFarePolicy();
        lineExtraFarePolicy = new LineExtraFarePolicy();
        fareDiscount = new AgeDiscountFarePolicy();
    }

    public Fare calculate(PathResult result, List<Integer> extraFares, Integer age){
        int basicFare = distanceFarePolicy.apply(result.getDistance());
        int extraFare = lineExtraFarePolicy.apply(extraFares);

        return new Fare(fareDiscount.apply(basicFare + extraFare, age));
    }
}
