package subway.path.domain;

import subway.line.domain.Line;
import subway.path.dto.PathResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FareCalculator {
    private FareByDistance fareByDistance;
    private FareByLine fareByLine;
    private FareByAge fareDiscount;

    public FareCalculator (){
        fareByDistance = new FareByDistance();
        fareByLine = new FareByLine();
        fareDiscount = new FareByAge();
    }

    public Fare calculate(PathResult result, List<Line> passingLines, Integer age){
        fareByDistance.calculateFare(result.getDistance());
        fareByLine.calculateFare(passingLines);

        return fareDiscount.calculateFare(fareByDistance.getFare() + fareByLine.getFare(), age);
    }
}
