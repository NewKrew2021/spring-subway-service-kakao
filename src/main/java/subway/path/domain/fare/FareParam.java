package subway.path.domain.fare;

public class FareParam {
    private final int lineExtraFare;
    private final int distance;

    public FareParam(int lineExtraFare, int distance) {
        this.lineExtraFare = lineExtraFare;
        this.distance = distance;
    }

    public static FareParam of(int lineExtraFare, int distance) {
        return new FareParam(lineExtraFare, distance);
    }

    public int getLineExtraFare() {
        return lineExtraFare;
    }

    public int getDistance() {
        return distance;
    }
}
