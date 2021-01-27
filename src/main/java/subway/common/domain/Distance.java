package subway.common.domain;

import subway.common.exception.NegativeNumberException;

public class Distance {
    private final int distance;

    private Distance(int distance) {
        validate(distance);
        this.distance = distance;
    }

    private void validate(int distance) {
        if (distance < 0) {
            throw new NegativeNumberException("거리는 음수가될 수 없습니다.");
        }
    }

    public static Distance from(int distance) {
        return new Distance(distance);
    }

    public static Distance min(Distance o1, Distance o2) {
        return o1.distance < o2.distance ? o1 : o2;
    }

    public int getDistance() {
        return distance;
    }

    public boolean isShorter(Distance o) {
        return distance < o.distance;
    }

    public Distance getDifference(Distance o) {
        int difference = distance > o.distance ? distance - o.distance : o.distance - distance;
        return Distance.from(difference);
    }

    public Distance getSum(Distance o) {
        return Distance.from(distance + o.distance);
    }
}
