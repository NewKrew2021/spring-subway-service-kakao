package subway.path.util;

import subway.exception.WrongInputDataException;

import java.util.stream.Stream;

public enum ComplimentaryAge {
    TODDLER(0, 5, 1), CHILD(6, 12, 0.5), TEENAGER(13, 18, 0.2), ADULT(19, 64, 0), ELDER(65, 1000, 1);

    private final int minAge;
    private final int maxAge;
    private final double complimentarySaleRate;

    ComplimentaryAge(int minAge, int maxAge, double complimentarySaleRate) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.complimentarySaleRate = complimentarySaleRate;
    }

    public static Stream<ComplimentaryAge> stream() {
        return Stream.of(ComplimentaryAge.values());
    }

    public static ComplimentaryAge getAgeGroup(int age) {
        return ComplimentaryAge.stream()
                .filter(it -> it.minAge <= age && it.maxAge >= age)
                .findAny()
                .orElseThrow(() -> new WrongInputDataException("나이를 잘못 입력하셨습니다."));
    }

    public double getComplimentarySaleRate() {
        return this.complimentarySaleRate;
    }

    public boolean isTargetOfFree() {
        if (this == TODDLER || this == ELDER) {
            return true;
        }
        return false;
    }
}
