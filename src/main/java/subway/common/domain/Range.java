package subway.common.domain;

public class Range {
    private final int includedLowerBound;
    private final int excludedUpperBound;

    private Range(int includedLowerBound, int excludedUpperBound){
        validate(includedLowerBound, excludedUpperBound);
        this.includedLowerBound = includedLowerBound;
        this.excludedUpperBound = excludedUpperBound;
    }

    private void validate(int includedLowerBound, int excludedUpperBound) {
        if(includedLowerBound >= excludedUpperBound) {
            throw new IllegalArgumentException("하한선은 상한선보다 작아야합니다.");
       }
    }

    public static Range of(int includedLowerBound, int excludedUpperBound) {
        return new Range(includedLowerBound, excludedUpperBound);
    }

    public boolean isBelong(int value) {
        return (includedLowerBound <= value && value < excludedUpperBound);
    }
}
