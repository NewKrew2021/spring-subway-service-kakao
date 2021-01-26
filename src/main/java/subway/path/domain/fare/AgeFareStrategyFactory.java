package subway.path.domain.fare;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AgeFareStrategyFactory {
    private static final List<AgeFareStrategy> fareStrategies =
            Arrays.asList(new KidFareStrategy(), new TeenagerFareStrategy());
    private static final AgeFareStrategy defaultAgeFareStrategy = new DefaultFareStrategy();

    public static AgeFareStrategy getInstance(int age) {
        Optional<AgeFareStrategy> strategy = fareStrategies.stream()
                .filter(ageFareStrategy -> ageFareStrategy.isInAge(age))
                .findFirst();
        return strategy.orElse(defaultAgeFareStrategy);
    }
}
