package subway.fare.domain;

import java.util.Arrays;

public enum AgeGrade {

    KID(5, 0.0, AgeGrade.DEDUCTION),
    CHILD(12, 0.5, AgeGrade.DEDUCTION),
    YOUTH(18, 0.8, AgeGrade.DEDUCTION),
    ADULT(Integer.MAX_VALUE, 1.0, 0);

    private static final int DEDUCTION = 350;

    public int age;
    public double discountRate;
    public int deduction;

    AgeGrade(int age, double discountRate, int deduction) {
        this.age = age;
        this.discountRate = discountRate;
        this.deduction = deduction;
    }

    public static AgeGrade of(int age) {
        return Arrays.stream(values())
                .filter(grade -> age <= grade.age)
                .findFirst()
                .orElse(ADULT);
    }
}
