package subway.path.domain.path.distance;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.path.domain.path.DistanceLineEdge;
import subway.path.domain.path.graph.PathAndArrival;
import subway.path.domain.path.graph.SubwayGraphElement;
import subway.path.domain.path.graph.SubwayGraph;
import subway.station.domain.Station;

import java.time.LocalDateTime;
import java.util.List;

public class ShortestDistanceGraph implements SubwayGraph {

    private final Graph<Station, DistanceLineEdge> graph;

    private ShortestDistanceGraph(Graph<Station, DistanceLineEdge> graph) {
        this.graph = graph;
    }

    public static ShortestDistanceGraph initialize(List<SubwayGraphElement> graphElements) {
        Graph<Station, DistanceLineEdge> graph = new WeightedMultigraph<>(DistanceLineEdge.class);
        for (SubwayGraphElement element : graphElements) {
            graph.addVertex(element.getSource());
            graph.addVertex(element.getTarget());
            graph.addEdge(element.getSource(), element.getTarget(), element.getEdge());
        }
        return new ShortestDistanceGraph(graph);
    }

    @Override
    public PathAndArrival getPath(Station source, Station target, LocalDateTime departureTime) {
        return new PathAndArrival() {
            @Override
            public GraphPath<Station, DistanceLineEdge> getPath() {
                return new DijkstraShortestPath<>(graph).getPath(source, target);
            }

            // TODO: 최단거리 도착 시간 구현
            @Override
            public LocalDateTime getArrivalTime() {
                return departureTime;
            }
        };
    }
}
