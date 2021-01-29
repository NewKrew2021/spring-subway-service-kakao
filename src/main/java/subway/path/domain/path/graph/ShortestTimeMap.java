package subway.path.domain.path.graph;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.jgrapht.graph.Multigraph;
import subway.line.domain.Line;
import subway.path.domain.path.SubwayEdge;
import subway.path.domain.path.SubwayPath;
import subway.station.domain.Station;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShortestTimeMap implements SubwayMap {

    private static final int ALL_PATH_COUNT = 1000;

    private final Graph<Station, SubwayEdge> graph;

    private ShortestTimeMap(Graph<Station, SubwayEdge> graph) {
        this.graph = graph;
    }

    public static ShortestTimeMap initialize(List<Line> lines) {
        return new ShortestTimeMap(GraphUtil.initializeGraph(new Multigraph<>(SubwayEdge.class), lines));
    }

    @Override
    public SubwayPath getPath(Station source, Station target, LocalDateTime departureTime) {
        return getShortestPath(getAllPathsWithArrival(source, target, departureTime));
    }

    private SubwayPath getShortestPath(Map<SubwayGraphPath, LocalDateTime> pathWithArrival) {
        return pathWithArrival.entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .map(entry -> SubwayPath.of(entry.getKey(), entry.getValue()))
                .orElseThrow(() -> new IllegalArgumentException("당일 도착하는 경로가 존재하지 않습니다"));
    }

    private Map<SubwayGraphPath, LocalDateTime> getAllPathsWithArrival(Station source, Station target, LocalDateTime departureTime) {
        Map<SubwayGraphPath, LocalDateTime> pathsWithArrival = new HashMap<>();

        for (SubwayGraphPath path : getAllPaths(source, target)) {
            path.findArrivalTime(departureTime)
                    .ifPresent(arrivalTime -> pathsWithArrival.put(path, arrivalTime));
        }
        return pathsWithArrival;
    }

    private List<SubwayGraphPath> getAllPaths(Station source, Station target) {
        try {
            return new KShortestPaths<>(graph, ALL_PATH_COUNT)
                    .getPaths(source, target)
                    .stream()
                    .map(SubwayGraphPath::new)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("시작역 또는 종착역이 그래프에 존재하지 않습니다");
        }
    }
}
