package subway.line.domain;


public class ExtraFare {
    private final int value;

    private ExtraFare(int value) {
        this.value = value;
    }

    public static ExtraFare of(int value){
        return new ExtraFare(value);
    }

    public int getValue() {
        return value;
    }
}
