package subway.path.domain.fare.strategy;

import subway.path.domain.Vertices;

public class LineFare implements FareStrategy {
    private final Vertices vertices;

    public LineFare(Vertices vertices) {
        this.vertices = vertices;
    }

    @Override
    public int getExtraFare() {
        return vertices.getMaxLineExtraFare();
    }
}
