package subway.path.domain.fare;

public abstract class AgeFareStrategy {
    abstract public int getDiscountedFare(int fare);

    abstract public int getMaxAge();

    abstract public int getMinAge();

    public boolean isInAge(int age) {
        return age >= getMinAge() && age < getMaxAge();
    }
}
