package subway.path.domain.path.time;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.jgrapht.graph.Multigraph;
import subway.line.domain.Line;
import subway.path.domain.path.SubwayEdge;
import subway.path.domain.path.graph.GraphUtil;
import subway.path.domain.path.graph.PathAndArrival;
import subway.path.domain.path.graph.SubwayGraph;
import subway.station.domain.Station;

import java.time.LocalDateTime;
import java.util.List;

public class ShortestTimeGraph implements SubwayGraph {

    private final Graph<Station, SubwayEdge> graph;

    private ShortestTimeGraph(Graph<Station, SubwayEdge> graph) {
        this.graph = graph;
    }

    public static ShortestTimeGraph initialize(List<Line> lines) {
        return new ShortestTimeGraph(GraphUtil.initializeGraph(new Multigraph<>(SubwayEdge.class), lines));
    }

    @Override
    public PathAndArrival getPath(Station source, Station target, LocalDateTime departureTime) {
        return new ShortestTimePathFinder(new KShortestPaths<>(graph, 1000).getPaths(source, target))
                .getPath(departureTime);
    }
}
