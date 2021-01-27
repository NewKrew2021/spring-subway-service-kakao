package subway.path.domain.path.graph;

import org.jgrapht.GraphPath;
import subway.path.domain.path.SubwayGraphEdge;
import subway.station.domain.Station;

import java.time.LocalDateTime;

public class PathAndArrival {

    private final GraphPath<Station, SubwayGraphEdge> path;
    private final LocalDateTime arrivalTime;

    public PathAndArrival(GraphPath<Station, SubwayGraphEdge> path, LocalDateTime arrivalTime) {
        this.path = path;
        this.arrivalTime = arrivalTime;
    }

    public GraphPath<Station, SubwayGraphEdge> getPath() {
        return path;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
}
