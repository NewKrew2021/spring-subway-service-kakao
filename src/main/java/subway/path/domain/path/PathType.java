package subway.path.domain.path;

import subway.line.domain.Line;
import subway.path.domain.path.graph.ShortestDistanceMap;
import subway.path.domain.path.graph.SubwayMap;
import subway.path.domain.path.graph.ShortestTimeMap;

import java.util.List;
import java.util.function.Function;

public enum PathType {
    DISTANCE(ShortestDistanceMap::initialize),
    ARRIVAL_TIME(ShortestTimeMap::initialize);

    private final Function<List<Line>, SubwayMap> graphInitializer;

    PathType(Function<List<Line>, SubwayMap> graphInitializer) {
        this.graphInitializer = graphInitializer;
    }

    public SubwayMap generateMapBy(List<Line> lines) {
        return graphInitializer.apply(lines);
    }
}
