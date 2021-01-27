package subway.member.domain;

public class Range {
    private int lowerBound;
    private int upperBound;

    private Range() {
    }

    private Range(int lowerBound, int upperBound) {
        validate(lowerBound, upperBound);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    private void validate(int lowerBound, int upperBound) {
        if(lowerBound > upperBound) {
            throw new IllegalArgumentException("하한선은 상한선보다 작거나 같아야합니다.");
       }
    }

    public static Range of(int lowerBound, int upperBound) {
        return new Range(lowerBound, upperBound);
    }

    public boolean isBelong(int value) {
        return (lowerBound <= value && value < upperBound);
    }
}
