package subway.path.domain;

import subway.line.domain.Line;

import java.util.List;

public class LineFare {

    public static int getFare(List<Line> lines) {
        int max = 0;
        for (Line line : lines) {
            max = Math.max(max, line.getExtraFare());
        }
        return max;
    }
}
