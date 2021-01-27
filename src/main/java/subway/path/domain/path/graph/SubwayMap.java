package subway.path.domain.path.graph;

import subway.line.domain.Line;
import subway.path.domain.path.PathType;
import subway.path.domain.path.SubwayPath;
import subway.station.domain.Station;

import java.time.LocalDateTime;
import java.util.List;

public class SubwayMap {

    private final SubwayGraph graph;

    private SubwayMap(SubwayGraph graph) {
        this.graph = graph;
    }

    public static SubwayMap from(List<Line> lines, PathType pathType) {
        return new SubwayMap(pathType.getGraphInitializer().apply(lines));
    }

    public SubwayPath getPath(Station source, Station target, LocalDateTime departureTime) {
        try {
            return SubwayPath.from(graph.getPath(source, target, departureTime));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("시작역 또는 종착역이 그래프에 존재하지 않습니다");
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("시작역에서 종착역으로 가는 최단 경로가 존재하지 않습니다");
        }
    }
}
