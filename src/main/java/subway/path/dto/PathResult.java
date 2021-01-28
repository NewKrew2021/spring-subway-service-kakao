package subway.path.dto;

import subway.path.domain.PathVertices;
import subway.station.domain.Station;

import java.util.List;

public class PathResult {
    private PathVertices pathVertices;
    private int distance;
    private int fare;

    public PathResult() {
    }
    public PathResult(PathVertices pathVertices, int distance) {
        this.pathVertices = pathVertices;
        this.distance = distance;
    }

    public PathResult(PathVertices pathVertices, int distance, int fare) {
        this.pathVertices = pathVertices;
        this.distance = distance;
        this.fare = fare;
    }

    public PathVertices getPathVertices() {
        return pathVertices;
    }

    public int getDistance() {
        return distance;
    }

    public int getFare() {
        return fare;
    }
}
