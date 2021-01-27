package subway.path.domain.fare;

import subway.common.domain.Fare;

public interface FareStrategy {
    Fare getFare();
}
