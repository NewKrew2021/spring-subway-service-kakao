package subway.member.domain;

import java.util.Arrays;

public enum AGE {
    BABY(0, 0, 6),
    CHILD(0.5, 175, 13),
    TEENAGER(0.8, 70, 19),
    ADULT(1L, 0, Integer.MAX_VALUE);

    private final double sale;
    private final int limitAge;
    private final int deduction;

    AGE(double sale, int deduction, int limitAge) {
        this.sale = sale;
        this.deduction = deduction;
        this.limitAge = limitAge;
    }

    public static AGE getAge(int age) {
        return Arrays.stream(values())
                .filter(a -> a.limitAge > age)
                .findFirst()
                .orElse(ADULT);
    }

    public double getSale() {
        return sale;
    }

    public int getDeduction() {
        return deduction;
    }
}
