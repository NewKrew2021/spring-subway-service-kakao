package subway.path.domain;


import static subway.path.domain.SubwayPolicy.*;

public class SubwayPrice {
    public static int getPrice(int distance, int maxExtraFare) {
        int price = DEFAULT_PRICE + maxExtraFare;

        if (distance <= MIN_DISTANCE) {
            return price;
        }
        if (distance <= MAX_DISTANCE) {
            return price + (distance - MIN_DISTANCE + 5 - 1) / 5 * ADDITIONAL_PRICE_FIVE_KM;
        }
        return getPrice(MAX_DISTANCE, maxExtraFare) + (distance - MAX_DISTANCE + 8 - 1) / 8 * ADDITIONAL_PRICE_EIGHT_KM;
    }

    public static int getDiscountPrice(int distance, int maxExtraFare, int age) {
        int priceWithoutDiscount = getPrice(distance, maxExtraFare);
        if (isTeenager(age)) {
            return (int) ((priceWithoutDiscount - AGE_DEDUCTION) * (1 - TEENAGER_DISCOUNT_RATE)) + AGE_DEDUCTION;
        }
        if (isChild(age)) {
            return (int) ((priceWithoutDiscount - AGE_DEDUCTION) * (1 - CHILD_DISCOUNT_RATE)) + AGE_DEDUCTION;
        }
        return priceWithoutDiscount;
    }

    private static boolean isTeenager(int age) {
        if (age >= TEENAGER_MIN_AGE && age < TEENAGER_MAX_AGE) {
            return true;
        }
        return false;
    }

    private static boolean isChild(int age) {
        if (age >= CHILD_MIN_AGE && age < CHILD_MAX_AGE) {
            return true;
        }
        return false;
    }
}
