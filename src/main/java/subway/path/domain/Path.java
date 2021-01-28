package subway.path.domain;

import subway.station.domain.Station;

import java.util.List;

public class Path {
    private static final int DEFAULT_FARE = 1250;
    private static final int EXTRA_FARE_PER_DISTANCE = 100;
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
        return (int) Math.ceil(Math.max(Math.min(distance, 50) - 10, 0) / 5.0) * EXTRA_FARE_PER_DISTANCE
                + (int) Math.ceil(Math.max(distance - 50, 0) / 8.0) * EXTRA_FARE_PER_DISTANCE;
    }
}
