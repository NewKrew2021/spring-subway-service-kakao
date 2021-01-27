package subway.path.domain.path;

import subway.path.domain.path.distance.ShortestDistancePathGraph;
import subway.path.domain.path.graph.SubwayGraphElement;
import subway.path.domain.path.graph.SubwayGraphPath;
import subway.path.domain.path.time.ShortestTimePathGraph;

import java.util.List;
import java.util.function.Function;

public enum PathType {
    DISTANCE(ShortestDistancePathGraph::initialize),
    ARRIVAL_TIME(ShortestTimePathGraph::initialize);

    private final Function<List<SubwayGraphElement>, SubwayGraphPath> graphInitializer;

    PathType(Function<List<SubwayGraphElement>, SubwayGraphPath> graphInitializer) {
        this.graphInitializer = graphInitializer;
    }

    public Function<List<SubwayGraphElement>, SubwayGraphPath> getGraphInitializer() {
        return graphInitializer;
    }
}
