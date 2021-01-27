package subway.path.domain.path.time;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.jgrapht.graph.Multigraph;
import subway.path.domain.path.DistanceLineEdge;
import subway.path.domain.path.graph.PathAndArrival;
import subway.path.domain.path.graph.SubwayGraphElement;
import subway.path.domain.path.graph.SubwayGraphPath;
import subway.station.domain.Station;

import java.time.LocalDateTime;
import java.util.List;

public class ShortestTimePathGraph implements SubwayGraphPath {

    private final Graph<Station, DistanceLineEdge> graph;

    private ShortestTimePathGraph(Graph<Station, DistanceLineEdge> graph) {
        this.graph = graph;
    }

    public static ShortestTimePathGraph initialize(List<SubwayGraphElement> graphElements) {
        Graph<Station, DistanceLineEdge> graph = new Multigraph<>(DistanceLineEdge.class);
        for (SubwayGraphElement element : graphElements) {
            graph.addVertex(element.getSource());
            graph.addVertex(element.getTarget());
            graph.addEdge(element.getSource(), element.getTarget(), element.getEdge());
        }
        return new ShortestTimePathGraph(graph);
    }

    @Override
    public PathAndArrival getPath(Station source, Station target, LocalDateTime departureTime) {
        return new ShortestTimePaths(new KShortestPaths<>(graph, 1000).getPaths(source, target))
                .getPath(departureTime);
    }
}
