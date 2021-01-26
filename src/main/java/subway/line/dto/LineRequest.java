package subway.line.dto;

import subway.line.domain.Line;

import java.time.LocalTime;

public class LineRequest {
    private final String name;
    private final String color;
    private final Long upStationId;
    private final Long downStationId;
    private final int distance;
    private final int extraFare;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final int timeInterval;

    public LineRequest(String name, String color, Long upStationId, Long downStationId, int distance, int extraFare,
                       LocalTime startTime, LocalTime endTime, int timeInterval) {
        this.name = name;
        this.color = color;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
        this.extraFare = extraFare;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeInterval = timeInterval;
    }

    public Line toEntity() {
        return new Line(name, color, extraFare, startTime, endTime, timeInterval);
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Long getUpStationId() {
        return upStationId;
    }

    public Long getDownStationId() {
        return downStationId;
    }

    public int getDistance() {
        return distance;
    }

    public int getExtraFare() {
        return extraFare;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public int getTimeInterval() {
        return timeInterval;
    }
}
