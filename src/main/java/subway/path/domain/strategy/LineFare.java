package subway.path.domain.strategy;

import subway.path.domain.Vertex;

import java.util.List;

public class LineFare implements FareStrategy {
    public static final int DEFAULT_EXTRA_FARE = 0;
    private final List<Vertex> vertexs;

    public LineFare(List<Vertex> vertexs) {
        this.vertexs = vertexs;
    }

    @Override
    public int apply(int fare) {
        return fare + vertexs.stream()
                .map(v -> v.getExtraFare())
                .max(Integer::compare).orElse(DEFAULT_EXTRA_FARE);
    }
}
