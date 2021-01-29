package subway.path.domain;

import java.util.stream.Stream;

public enum DistanceFare {

    OVER_FIFTY_KM(51, 8, 100, 800),
    OVER_TEN_KM(11, 5, 100, 0),
    DEFAULT(1, 10, 0, 0);

    private final int basisDistance;
    private final int unitDistance;
    private final int extraFarePerUnit;
    private final int defaultFare;

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
