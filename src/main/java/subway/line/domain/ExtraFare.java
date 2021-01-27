package subway.line.domain;

import subway.line.exception.LineNotFoundException;

import java.util.List;

public class ExtraFare {
    private final int value;

    private ExtraFare(int value) {
        this.value = value;
    }

    public static ExtraFare of(int value){
        return new ExtraFare(value);
    }

    public static ExtraFare of(SubwayMap subwayMap, List<DirectedSection> directedSections) {
        return new ExtraFare(directedSections.stream()
                .mapToInt(directedSection-> subwayMap.getExtraFare(
                        directedSection.getUpStation(),
                        directedSection.getDownStation()))
                .max().orElseThrow(LineNotFoundException::new));
    }

    public int getValue() {
        return value;
    }
}
