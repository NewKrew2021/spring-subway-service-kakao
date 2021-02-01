package subway.line.domain;

public enum DistancePolicy {
    PER_NEAR_DISTANCE(5),
    PER_FAR_DISTANCE(8),
    MIN_DISTANCE(10),
    MAX_DISTANCE(50);

    int value;

    DistancePolicy(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
