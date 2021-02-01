package subway.common.domain;

import subway.common.exception.NegativeNumberException;
import subway.common.exception.NotExistException;

import java.util.Objects;

public class Distance {
    private final int distance;

    private Distance(int distance) {
        validate(distance);
        this.distance = distance;
    }

    private void validate(int distance) {
        if (distance < 0) {
            throw new NegativeNumberException("거리는 음수가 될 수 없습니다.");
        }
    }

    public static Distance from(int distance) {
        return new Distance(distance);
    }

    public static Distance min(Distance o1, Distance o2) {
        if (o1 == null || o2 == null) {
            throw new NotExistException("존재하지 않는 거리는 비교할 수 없습니다.");
        }
        return o1.distance < o2.distance ? o1 : o2;
    }

    public int getDistance() {
        return distance;
    }

    public boolean isShorter(Distance o) {
        if (o == null) {
            throw new NotExistException("존재하지 않는 거리는 비교할 수 없습니다.");
        }
        return distance < o.distance;
    }

    public Distance getDifference(Distance subtrahend) {
        if (subtrahend == null) {
            throw new NotExistException("존재하지 않는 거리는 연산할 수 없습니다.");
        }
        int difference = distance > subtrahend.distance ? distance - subtrahend.distance : subtrahend.distance - distance;
        return Distance.from(difference);
    }

    public Distance getSum(Distance addend) {
        if (addend == null) {
            throw new NotExistException("존재하지 않는 거리는 연산할 수 없습니다.");
        }
        return Distance.from(distance + addend.distance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distance distance1 = (Distance) o;
        return distance == distance1.distance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance);
    }
}
