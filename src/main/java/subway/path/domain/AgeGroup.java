package subway.path.domain;

public enum AgeGroup {
    ADULT(1),
    TEENAGER(0.8),
    CHILD(0.5),
    TODDLER(0);

    private final double fareRate;

    AgeGroup(double fareRate) {
        this.fareRate = fareRate;
    }

    public double getFareRate() {
        return this.fareRate;
    }

    public static AgeGroup getAgeGroup(int age) {
        if(age < 6) {
            return TODDLER;
        }
        if(age < 13) {
            return CHILD;
        }
        if(age < 19) {
            return TEENAGER;
        }
        return ADULT;
    }

}
