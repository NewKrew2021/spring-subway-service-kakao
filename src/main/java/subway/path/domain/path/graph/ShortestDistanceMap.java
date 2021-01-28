package subway.path.domain.path.graph;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.path.domain.path.SubwayEdge;
import subway.path.domain.path.SubwayPath;
import subway.station.domain.Station;

import java.time.LocalDateTime;
import java.util.List;

public class ShortestDistanceMap implements SubwayMap {

    private final Graph<Station, SubwayEdge> graph;

    private ShortestDistanceMap(Graph<Station, SubwayEdge> graph) {
        this.graph = graph;
    }

    public static ShortestDistanceMap initialize(List<Line> lines) {
        return new ShortestDistanceMap(GraphUtil.initializeGraph(new WeightedMultigraph<>(SubwayEdge.class), lines));
    }

    @Override
    public SubwayPath getPath(Station source, Station target, LocalDateTime departureTime) {
        SubwayGraphPath path = getShortestPath(source, target);
        return SubwayPath.of(path, getArrivalTime(path, departureTime));
    }

    private SubwayGraphPath getShortestPath(Station source, Station target) {
        try {
            return new SubwayGraphPath(new DijkstraShortestPath<>(graph).getPath(source, target));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("시작역 또는 종착역이 그래프에 존재하지 않습니다");
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("시작역에서 종착역으로 가는 최단 경로가 존재하지 않습니다");
        }
    }

    private LocalDateTime getArrivalTime(SubwayGraphPath path, LocalDateTime departureTime) {
        return path.findArrivalTime(departureTime)
                .orElseThrow(() -> new IllegalArgumentException("당일 도착하는 경로가 존재하지 않습니다"));
    }
}
