package subway.path.domain.strategy;

import subway.member.domain.AGE;

public class AgeFare implements FareStrategy {
    private final AGE age;
    public AgeFare(AGE age) {
        this.age = age;
    }

    @Override
    public int apply(int fare) {
        return (int) (fare * age.getSale() + age.getDeduction());
    }
}
