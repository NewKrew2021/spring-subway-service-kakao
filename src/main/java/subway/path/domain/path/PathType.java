package subway.path.domain.path;

import subway.line.domain.Line;
import subway.path.domain.path.distance.ShortestDistanceGraph;
import subway.path.domain.path.graph.SubwayGraph;
import subway.path.domain.path.time.ShortestTimeGraph;

import java.util.List;
import java.util.function.Function;

public enum PathType {
    DISTANCE(ShortestDistanceGraph::initialize),
    ARRIVAL_TIME(ShortestTimeGraph::initialize);

    private final Function<List<Line>, SubwayGraph> graphInitializer;

    PathType(Function<List<Line>, SubwayGraph> graphInitializer) {
        this.graphInitializer = graphInitializer;
    }

    public Function<List<Line>, SubwayGraph> getGraphInitializer() {
        return graphInitializer;
    }
}
