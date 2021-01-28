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
        // TODO: 도착 시간 구현
        try {
            return SubwayPath.of(new DijkstraShortestPath<>(graph).getPath(source, target), departureTime);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("시작역 또는 종착역이 그래프에 존재하지 않습니다");
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("시작역에서 종착역으로 가는 최단 경로가 존재하지 않습니다");
        }
    }
}
