package subway.path.domain;

import java.util.stream.Stream;

public enum DistanceFare {

    OVER_FIFTY_KM(51, 8, 100, 2050),
    OVER_TEN_KM(11, 5, 100, 1250),
    DEFAULT(1, 10, 1250, 0);

    private int basisDistance;
    private int unitDistance;
    private int extraFarePerUnit;
    private int defaultFare;

    DistanceFare(int basisDistance, int unitDistance, int extraFarePerUnit, int defaultFare) {
        this.basisDistance = basisDistance;
        this.unitDistance = unitDistance;
        this.extraFarePerUnit = extraFarePerUnit;
        this.defaultFare = defaultFare;
    }

    public static DistanceFare getDistanceFare(int distance) {
        return Stream.of(DistanceFare.values())
                .filter(distanceFare -> distance >= distanceFare.basisDistance)
                .findFirst()
                .orElse(DEFAULT);
    }

    public int calculateExtraFareByDistance(int distance) {
        return ((distance - basisDistance) / unitDistance + 1) * extraFarePerUnit + defaultFare;
    }
}
