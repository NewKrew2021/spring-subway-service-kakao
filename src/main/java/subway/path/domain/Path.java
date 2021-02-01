package subway.path.domain;

import org.jgrapht.GraphPath;
import subway.station.domain.Station;

import java.util.List;
import java.util.stream.Collectors;

public class Path {
    private final GraphPath<Station, Edge> path;

    public Path(GraphPath<Station, Edge> shortestPath) {
        this.path = shortestPath;
    }

    public List<Station> getStations() {
        return path.getVertexList();
    }

    public int getDistance() {
        return (int)path.getWeight();
    }

    public List<Integer> getExtraFaresInEdges() {
        return path.getEdgeList()
                .stream()
                .map(Edge::getExtraFare)
                .collect(Collectors.toList());
    }
}
