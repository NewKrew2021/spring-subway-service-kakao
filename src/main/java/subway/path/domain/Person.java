package subway.path.domain;

import subway.member.domain.DefaultMember;
import subway.member.domain.LoginMember;

import java.util.stream.Stream;

public enum Person {
    ADULT(0, 0, 19, Person.MAX_AGE),
    TEEN(20, 350, 13, 19),
    CHILD(50, 350, 6, 13),
    NEWBORN(100, 0, 0, 6);

    private static final int MAX_AGE = 999;
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

    public static Person of(LoginMember loginMember) {
        if (loginMember == null || loginMember instanceof DefaultMember) {
            return ADULT;
        }

        return Stream.of(values())
                .filter(value -> value.isInAge(loginMember.getAge()))
                .findFirst()
                .orElse(ADULT);
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public int getDeduction() {
        return deduction;
    }

    private boolean isInAge(int age) {
        return minAge <= age && age < maxAge;
    }
}
