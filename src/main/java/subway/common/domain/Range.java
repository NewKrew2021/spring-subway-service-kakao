package subway.common.domain;

public class Range {
    private final int lowerBound;
    private final int upperBound;

    private Range(int lowerBound, int upperBound) {
        validate(lowerBound, upperBound);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    private void validate(int lowerBound, int upperBound) {
        if(lowerBound >= upperBound) {
            throw new IllegalArgumentException("하한선은 상한선보다 작아야합니다.");
       }
    }

    public static Range of(int lowerBound, int upperBound) {
        return new Range(lowerBound, upperBound);
    }

    public boolean isBelong(int value) {
        return (lowerBound <= value && value < upperBound);
    }
}
