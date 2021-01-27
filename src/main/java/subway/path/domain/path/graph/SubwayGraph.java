package subway.path.domain.path.graph;

import subway.line.domain.Line;
import subway.path.domain.path.DistanceLineEdge;
import subway.path.domain.path.PathType;
import subway.path.domain.path.SubwayPath;
import subway.station.domain.Station;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SubwayGraph {

    private final SubwayGraphPath graph;

    private SubwayGraph(SubwayGraphPath graph) {
        this.graph = graph;
    }

    public static SubwayGraph from(List<Line> lines, PathType type) {
        return new SubwayGraph(type.getGraphInitializer().apply(getGraphElements(lines)));
    }

    private static List<SubwayGraphElement> getGraphElements(List<Line> lines) {
        return lines.stream()
                .map(SubwayGraph::toElements)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private static List<SubwayGraphElement> toElements(Line line) {
        return line.getSections()
                .stream()
                .map(section -> new SubwayGraphElement(
                        section.getUpStation(),
                        section.getDownStation(),
                        new DistanceLineEdge(section.getDistance(), line)
                ))
                .collect(Collectors.toList());
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
