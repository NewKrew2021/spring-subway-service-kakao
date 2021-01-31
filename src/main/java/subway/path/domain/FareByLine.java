package subway.path.domain;

import subway.line.domain.Line;

import java.util.List;
import java.util.stream.Collectors;

public class FareByLine extends Fare{
    public FareByLine(List<Line> lines) {
        super(getExtraFareByLines(lines.stream()
                .map(Line::getExtraFare)
                .collect(Collectors.toList())));
    }

    private static int getExtraFareByLines(List<Integer> extraFareList){
        return extraFareList.stream().max(Integer::compare).orElse(0);
    }
}
