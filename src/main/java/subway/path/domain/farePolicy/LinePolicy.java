package subway.path.domain.farePolicy;

import subway.line.domain.Line;

import java.util.List;

public class LinePolicy extends ExtraFare {
    private static final int INITIAL_PAYMENT = 0;

    private List<Line> lines;
    private BasicFare fare;

    public LinePolicy(BasicFare fare, List<Line> lines) {
        this.lines = lines;
        this.fare = fare;
    }

    @Override
    public int getFare() {
        return fare.getFare() + getExtraFare();
    }

    @Override
    public int getExtraFare() {
        return lines.stream()
                .mapToInt(Line::getExtraFare)
                .max()
                .orElse(INITIAL_PAYMENT);
    }

}
