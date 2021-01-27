package subway.path.domain.path.time;

import org.jgrapht.GraphPath;
import subway.path.domain.path.DistanceLineEdge;
import subway.path.domain.path.graph.PathAndArrival;
import subway.station.domain.Station;

import java.time.LocalDateTime;
import java.util.List;

public class ShortestTimePaths {

    private final List<GraphPath<Station, DistanceLineEdge>> graphPaths;

    public ShortestTimePaths(List<GraphPath<Station, DistanceLineEdge>> graphPaths) {
        this.graphPaths = graphPaths;
    }

    public PathAndArrival getPath(LocalDateTime departureTime) {

        return new PathAndArrival() {
            // TODO: 최단 시간 도착 경로 구현
            @Override
            public GraphPath<Station, DistanceLineEdge> getPath() {
                return graphPaths.get(0);
            }

            // TODO: 최단 시간 도착 시간 구현
            @Override
            public LocalDateTime getArrivalTime() {
                return departureTime;
            }
        };
    }
}
