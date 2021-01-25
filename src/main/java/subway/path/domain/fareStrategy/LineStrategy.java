package subway.path.domain.fareStrategy;

import org.jgrapht.GraphPath;
import subway.line.domain.Line;
import subway.path.domain.WeightWithLine;

import java.util.List;
import java.util.stream.Collectors;

public class LineStrategy implements FareStrategy {
    public static final int INITIAL_FARE = 0;

    private List<Line> lines;
    private int fare;

    public LineStrategy(int fare , GraphPath graphPath) {
        List<WeightWithLine> weightWithLines = graphPath.getEdgeList();
        this.lines = weightWithLines.stream().map(WeightWithLine::getLine).collect(Collectors.toList());
        this.fare = fare;
    }

    @Override
    public int getFare() {
        return fare + lines.stream()
                .mapToInt(Line::getExtraFare)
                .max()
                .orElse(INITIAL_FARE);
    }
}
