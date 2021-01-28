package subway.path.domain;

import java.util.stream.Stream;

public enum Person {
    ADULT(0, 0, 19, Person.NO_LIMIT),
    TEEN(20, 350, 13, 19),
    CHILD(50, 350, 6, 13),
    NEWBORN(100, 0, 0, 6);

    private static final int NO_LIMIT = 100000;
    private final int discountPercentage;
    private final int deduction;
    private final int minAge;
    private final int maxAge;

    Person(int discountPercentage, int deduction, int minAge, int maxAge) {
        this.discountPercentage = discountPercentage;
        this.deduction = deduction;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public static Person of(long age) {
        return Stream.of(values())
                .filter(value -> value.isInAge(age))
                .findFirst()
                .orElse(ADULT);
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public int getDeduction() {
        return deduction;
    }

    public int getMinAge() {
        return minAge;
    }

    private boolean isInAge(long age) {
        return minAge <= age && age < maxAge;
    }
}
