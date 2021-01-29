package subway.path.domain;

public enum DiscountAge {
    BABY(1, 5, 1),
    CHILD(6, 12, 0.5),
    YOUTH(13, 19, 0.2);

    private static final int BABY_FARE = 0;
    private static final int DEDUCTION_FARE = 350;

    private int minAge;
    private int maxAge;
    private double discountRate;

    DiscountAge(int minAge, int maxAge, double discountRate) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.discountRate = discountRate;
    }

    public static int getTotalFareToDiscountAge(int totalFare, int age) {
        if (BABY.isAge(age)) {
            return BABY_FARE;
        }

        if (CHILD.isAge(age)) {
            return totalFare - CHILD.getDiscountAmount(totalFare);
        }

        if (YOUTH.isAge(age)) {
            return totalFare - YOUTH.getDiscountAmount(totalFare);
        }

        return totalFare;
    }

    private int getDiscountAmount(int totalFare) {
        return (int) ((totalFare - DEDUCTION_FARE) * this.discountRate);
    }

    private boolean isAge(int age) {
        return this.minAge <= age && age <= this.maxAge;
    }

}
