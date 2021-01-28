package subway.line.dto;

import subway.exception.InvalidInputException;

public class LineRequest {
    private String name;
    private String color;
    private Long upStationId;
    private Long downStationId;
    private int distance;
    private int extraFare;

    public LineRequest() {
    }

    public LineRequest(String name, String color, Long upStationId, Long downStationId, int distance) {
        validInput(name, color, upStationId, downStationId, distance);


        this.name = name;
        this.color = color;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public LineRequest(String name, String color, Long upStationId, Long downStationId, int distance, int extraFare) {
        if (extraFare < 0) {
            throw new InvalidInputException("유효하지 않은 추가금액입니다.");
        }
        this.name = name;
        this.color = color;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
        this.extraFare = extraFare;
    }

    private void validInput(String name, String color, Long upStationId, Long downStationId, int distance) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("이름 입력 존재하지 않습니다.");
        }
        if (color == null || color.trim().isEmpty()) {
            throw new InvalidInputException("색 입력이 존재하지 않습니다.");
        }
        if (upStationId == null || upStationId < 0) {
            throw new InvalidInputException("상행점 아이디가 입력되지 않았거나, 유효하지 않은 아이디입니다");
        }
        if (downStationId == null || downStationId < 0) {
            throw new InvalidInputException("행점 아이디가 입력되지 않았거나, 유효하지 않은 아이디입니다");
        }
        if (distance < 0) {
            throw new InvalidInputException("유효하지 않은 거리입니다");
        }
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

}
