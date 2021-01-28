package subway.line.vo;

import subway.line.dto.LineRequest;

public class LineAttributes {
    private final String name;
    private final String color;
    private final int extraFare;

    public LineAttributes(LineRequest lineRequest) {
        this.name = lineRequest.getName();
        this.color = lineRequest.getColor();
        this.extraFare = lineRequest.getExtraFare();
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getExtraFare() {
        return extraFare;
    }
}
