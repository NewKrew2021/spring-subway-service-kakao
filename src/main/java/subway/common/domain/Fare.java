package subway.common.domain;

import subway.common.exception.NegativeNumberException;
import subway.common.exception.NotExistException;

import java.util.Objects;

public class Fare {
    private final int fare;

    private Fare(int fare) {
        validate(fare);
        this.fare = fare;
    }

    private void validate(int fare) {
        if (fare < 0) {
            throw new NegativeNumberException("요금은 음수가 될 수 없습니다.");
        }
    }

    public static Fare from(int fare) {
        return new Fare(fare);
    }

    public static Fare max(Fare f1, Fare f2) {
        if (f1 == null || f2 == null) {
            throw new NotExistException("존재하지 않는 요금은 비교할 수 없습니다.");
        }
        return f1.fare > f2.fare ? f1 : f2;
    }

    public int getFare() {
        return fare;
    }

    public static Fare add(Fare f1, Fare f2) {
        if (f1 == null || f2 == null) {
            throw new NotExistException("존재하지 않는 요금은 연산할 수 없습니다.");
        }
        return new Fare(f1.fare + f2.fare);
    }

    public Fare sub(Fare f) {
        if (f == null) {
            throw new NotExistException("존재하지 않는 요금은 연산할 수 없습니다.");
        }
        return new Fare(fare - f.fare);
    }

    public Fare multiply(int number) {
        return new Fare(fare * number);
    }

    public Fare multiply(double number) {
        return new Fare((int) (fare * number));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fare fare1 = (Fare) o;
        return fare == fare1.fare;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fare);
    }
}
