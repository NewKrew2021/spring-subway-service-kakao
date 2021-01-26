package subway.path.domain;

public class SubwayWeight {
    private int extraFare;
    private int distance;

    public SubwayWeight(int extraFare, int distance) {
        this.extraFare = extraFare;
        this.distance = distance;
    }

    public int getExtraFare() {
        return extraFare;
    }

    public int getDistance() {
        return distance;
    }
}
