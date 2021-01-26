package subway.path.domain;

public class SubwayWeight {

    private final int fare;
    private final int distance;

    public SubwayWeight(int fare, int distance) {
        this.fare = fare;
        this.distance = distance;
    }

    public int getFare() {
        return fare;
    }

    public int getDistance() {
        return distance;
    }
}
