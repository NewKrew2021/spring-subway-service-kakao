package subway.path.domain.path.distance;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.path.domain.path.SubwayGraphEdge;
import subway.path.domain.path.graph.GraphUtil;
import subway.path.domain.path.graph.PathAndArrival;
import subway.path.domain.path.graph.SubwayGraph;
import subway.station.domain.Station;

import java.time.LocalDateTime;
import java.util.List;

public class ShortestDistanceGraph implements SubwayGraph {

    private final Graph<Station, SubwayGraphEdge> graph;

    private ShortestDistanceGraph(Graph<Station, SubwayGraphEdge> graph) {
        this.graph = graph;
    }

    public static ShortestDistanceGraph initialize(List<Line> lines) {
        return new ShortestDistanceGraph(GraphUtil.initializeGraph(new WeightedMultigraph<>(SubwayGraphEdge.class), lines));
    }

    @Override
    public PathAndArrival getPath(Station source, Station target, LocalDateTime departureTime) {
        // TODO: 도착 시간 구현
        return new PathAndArrival(new DijkstraShortestPath<>(graph).getPath(source, target), departureTime);
    }
}
