package subway.path.domain.path;

import subway.line.domain.Line;
import subway.path.domain.path.graph.GraphElement;
import subway.path.domain.path.graph.ShortestPathGraph;
import subway.station.domain.Station;

import java.util.List;
import java.util.stream.Collectors;

public class SubwayGraph {

    private final ShortestPathGraph<Station, DistanceLineEdge> graph;

    private SubwayGraph(ShortestPathGraph<Station, DistanceLineEdge> graph) {
        this.graph = graph;
    }

    public static SubwayGraph from(List<Line> lines) {
        return new SubwayGraph(ShortestPathGraph.initialize(getGraphElements(lines), DistanceLineEdge.class));
    }

    private static List<GraphElement<Station, DistanceLineEdge>> getGraphElements(List<Line> lines) {
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

    public SubwayPath getPath(Station source, Station target) {
        try {
            return SubwayPath.from(graph.getPath(source, target));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("시작역 또는 종착역이 그래프에 존재하지 않습니다");
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("시작역에서 종착역으로 가는 최단 경로가 존재하지 않습니다");
        }
    }
}
