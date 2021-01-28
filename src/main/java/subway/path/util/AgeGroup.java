package subway.path.util;

public enum AgeGroup {
    BABY, CHILD, TEENAGER, ADULT;

    private static final int MIN_CHILD_AGE = 6;
    private static final int MIN_TEENAGER_AGE = 13;
    private static final int MIN_ADULT_AGE = 19;

    public static AgeGroup getAgeGroup(int age) {
        if (age < MIN_CHILD_AGE) {
            return BABY;
        }
        if (age < MIN_TEENAGER_AGE) {
            return CHILD;
        }
        if (age < MIN_ADULT_AGE) {
            return TEENAGER;
        }
        return ADULT;
    }

    public static double getDiscountRate(AgeGroup ageGroup) {
        if (ageGroup == BABY) {
            return 1.0;
        }
        if (ageGroup == CHILD) {
            return 0.5;
        }
        if (ageGroup == TEENAGER) {
            return 0.2;
        }
        return 0.0;
    }

    public static int getDeduction(AgeGroup ageGroup) {
        if (ageGroup == BABY) {
            return 0;
        }
        if (ageGroup == CHILD) {
            return 350;
        }
        if (ageGroup == TEENAGER) {
            return 350;
        }
        return 0;
    }
}
