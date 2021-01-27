package subway.path.domain.path.graph;

import org.jgrapht.GraphPath;
import subway.path.domain.path.SubwayEdge;
import subway.station.domain.Station;

import java.time.LocalDateTime;

public class PathAndArrival {

    private final GraphPath<Station, SubwayEdge> path;
    private final LocalDateTime arrivalTime;

    public PathAndArrival(GraphPath<Station, SubwayEdge> path, LocalDateTime arrivalTime) {
        this.path = path;
        this.arrivalTime = arrivalTime;
    }

    public GraphPath<Station, SubwayEdge> getPath() {
        return path;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
}
