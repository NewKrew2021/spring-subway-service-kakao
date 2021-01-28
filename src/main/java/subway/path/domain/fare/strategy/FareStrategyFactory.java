package subway.path.domain.fare.strategy;

import subway.path.domain.fare.FareParam;

import java.util.Arrays;
import java.util.List;

public class FareStrategyFactory {
    public static List<FareStrategy> createBy(FareParam fareParam) {
        List<FareStrategy> fareStrategies = Arrays.asList(
                new LineFare(fareParam.getVertices()),
                new DistanceFare(fareParam.getDistance())
        );
        return fareStrategies;
    }
}
