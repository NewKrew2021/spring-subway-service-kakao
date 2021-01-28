package subway.line.dto;

import subway.exception.InvalidInputException;

public class SectionRequest {
    private Long upStationId;
    private Long downStationId;
    private int distance;

    public SectionRequest() {
    }

    public SectionRequest(Long upStationId, Long downStationId, int distance) {
        validInput(upStationId, downStationId, distance);
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    private void validInput(Long upStationId, Long downStationId, int distance) {
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

    public Long getUpStationId() {
        return upStationId;
    }

    public Long getDownStationId() {
        return downStationId;
    }

    public int getDistance() {
        return distance;
    }

}
