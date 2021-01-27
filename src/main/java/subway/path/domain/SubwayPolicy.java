package subway.path.domain;

public class SubwayPolicy {
    static final int DEFAULT_PRICE = 1250;

    static final int MIN_DISTANCE = 10;
    static final int MAX_DISTANCE = 50;

    static final int ADDITIONAL_PRICE_FIVE_KM = 100;
    static final int ADDITIONAL_PRICE_EIGHT_KM = 100;

    static final int TEENAGER_MIN_AGE = 13;
    static final int TEENAGER_MAX_AGE = 19;
    static final int CHILD_MIN_AGE = 6;
    static final int CHILD_MAX_AGE = 13;

    static final int AGE_DEDUCTION = 350;
    static final float TEENAGER_DISCOUNT_RATE = 0.2f;
    static final float CHILD_DISCOUNT_RATE = 0.5f;
}
