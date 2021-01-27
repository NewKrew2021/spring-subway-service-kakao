package subway.path.domain;

import subway.station.domain.Station;

import java.util.List;

public class Path {
    private static final int DEFAULT_FARE = 1250;
    private static final int EXTRA_FARE_PER_UNIT_DISTANCE = 100;
    private static final int SECTION_BOUNDARY = 50;
    private static final double FIRST_SECTION_DIVIDE_UNIT = 5.0;
    private static final double SECOND_SECTION_DIVIDE_UNIT = 8.0;
    private static final int DISTANCE_FREE_OF_CHARGE = 10;
    private final List<Station> path;
    private final List<SectionEdge> sectionEdges;
    private final int distance;

    public Path(List<Station> path, List<SectionEdge> sectionEdges, int distance) {
        this.path = path;
        this.sectionEdges = sectionEdges;
        this.distance = distance;
    }

    public int calculateFare() {
        return DEFAULT_FARE + calculateDistanceExtraFare() + calculateLineExtraFare();
    }

    public List<Station> getPath() {
        return path;
    }

    public List<SectionEdge> getSectionEdges() {
        return sectionEdges;
    }

    public int getDistance() {
        return distance;
    }

    private int calculateLineExtraFare() {
        return sectionEdges.stream()
                .map(SectionEdge::getFare)
                .max(Integer::compare)
                .orElse(0);
    }

    private int calculateDistanceExtraFare() {
        return fareOfFirstBoundary() + fareOfSecondBoundary();
    }

    private int fareOfFirstBoundary() {
        int chargingDistance = Math.min(distance, SECTION_BOUNDARY) - DISTANCE_FREE_OF_CHARGE;
        if (isNegative(chargingDistance)) {
            return 0;
        }

        int chargeUnits = (int) Math.ceil(chargingDistance / FIRST_SECTION_DIVIDE_UNIT);
        return chargeUnits * EXTRA_FARE_PER_UNIT_DISTANCE;
    }

    private int fareOfSecondBoundary() {
        int chargingDistance = distance - SECTION_BOUNDARY;
        if (isNegative(chargingDistance)) {
            return 0;
        }

        int chargeUnits = (int) Math.ceil(chargingDistance / SECOND_SECTION_DIVIDE_UNIT);
        return chargeUnits * EXTRA_FARE_PER_UNIT_DISTANCE;
    }

    private boolean isNegative(int chargingDistance) {
        return chargingDistance < 0;
    }
}
