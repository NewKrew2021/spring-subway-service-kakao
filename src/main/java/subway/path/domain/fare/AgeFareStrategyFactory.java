package subway.path.domain.fare;

public class AgeFareStrategyFactory {
    private static final AgeFareStrategy kidFareStrategy = new KidFareStrategy();
    private static final AgeFareStrategy teenagerFareStrategy = new TeenagerFareStrategy();
    private static final AgeFareStrategy defaultAgeFareStrategy = new DefaultFareStrategy();

    public static AgeFareStrategy getInstance(int age) {
        if (age >= 6 && age < 13) {
            return kidFareStrategy;
        }
        if (age >= 13 && age < 19) {
            return teenagerFareStrategy;
        }
        return defaultAgeFareStrategy;
    }
}
