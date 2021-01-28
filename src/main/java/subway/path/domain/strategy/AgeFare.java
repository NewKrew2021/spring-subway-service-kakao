package subway.path.domain.strategy;

import subway.member.domain.Age;

public class AgeFare implements FareStrategy {
    private final Age age;
    public AgeFare(Age age) {
        this.age = age;
    }

    @Override
    public int apply(int fare) {
        return (int) (fare * age.getSale() + age.getDeduction());
    }
}
