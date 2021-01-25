package subway.path.domain.path;

import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.path.domain.path.graph.Graph;
import subway.path.domain.path.graph.ShortestPathGraph;
import subway.station.domain.Station;

import java.util.List;

public class SubwayGraph {

    private final Graph<Station, DistanceLineEdge> graph;

    private SubwayGraph(Graph<Station, DistanceLineEdge> graph) {
        this.graph = graph;
    }

    public static SubwayGraph from(List<Line> lines) {
        Graph<Station, DistanceLineEdge> graph = ShortestPathGraph.initialize(DistanceLineEdge.class);
        for (Line line : lines) {
            generateGraph(graph, line);
        }
        return new SubwayGraph(graph);
    }

    private static void generateGraph(Graph<Station, DistanceLineEdge> graph, Line line) {
        for (Section section : line.getSections()) {
            graph.add(
                    section.getUpStation(),
                    section.getDownStation(),
                    new DistanceLineEdge(section.getDistance(), line)
            );
        }
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
