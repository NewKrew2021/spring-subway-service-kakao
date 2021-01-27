package subway.path.domain.path;

import subway.path.domain.path.distance.ShortestDistanceGraph;
import subway.path.domain.path.graph.SubwayGraphElement;
import subway.path.domain.path.graph.SubwayGraph;
import subway.path.domain.path.time.ShortestTimeGraph;

import java.util.List;
import java.util.function.Function;

public enum PathType {
    DISTANCE(ShortestDistanceGraph::initialize),
    ARRIVAL_TIME(ShortestTimeGraph::initialize);

    private final Function<List<SubwayGraphElement>, SubwayGraph> graphInitializer;

    PathType(Function<List<SubwayGraphElement>, SubwayGraph> graphInitializer) {
        this.graphInitializer = graphInitializer;
    }

    public Function<List<SubwayGraphElement>, SubwayGraph> getGraphInitializer() {
        return graphInitializer;
    }
}
