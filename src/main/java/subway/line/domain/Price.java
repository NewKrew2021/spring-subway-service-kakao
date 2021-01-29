package subway.line.domain;

public enum Price {
    DEFAULT_PRICE(1250),
    ADDITIONAL_PRICE(100),
    FARE_FREE_PRICE(350),
    RATE_HALF(2),
    RATE_FIFTH(5);

    int value;

    Price(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
