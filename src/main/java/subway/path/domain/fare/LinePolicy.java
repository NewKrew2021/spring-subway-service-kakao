package subway.path.domain.fare;

import subway.line.domain.Line;

import java.util.List;

public class LinePolicy implements FarePolicy {

    private static final int DEFAULT_EXTRA_FARE = 0;

    private final List<Line> lines;

    public LinePolicy(List<Line> lines) {
        this.lines = lines;
    }

    @Override
    public int apply(int fare) {
        return fare + getExtraFare();
    }

    private int getExtraFare() {
        return lines.stream()
                .mapToInt(Line::getExtraFare)
                .max()
                .orElse(DEFAULT_EXTRA_FARE);
    }
}
