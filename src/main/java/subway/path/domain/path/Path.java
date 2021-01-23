package subway.path.domain.path;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Path {

    private final GraphPath<Station, DistanceLineEdge> path;

    private Path(GraphPath<Station, DistanceLineEdge> path) {
        this.path = Objects.requireNonNull(path);
    }

    public static Path from(List<Line> lines, Station source, Station target) {
        WeightedMultigraph<Station, DistanceLineEdge> graph = new WeightedMultigraph<>(DistanceLineEdge.class);
        for (Line line : lines) {
            generateGraph(graph, line);
        }

        return generatePath(source, target, graph);
    }

    private static void generateGraph(WeightedMultigraph<Station, DistanceLineEdge> graph, Line line) {
        for (Section section : line.getSections()) {
            Station upStation = section.getUpStation();
            Station downStation = section.getDownStation();

            graph.addVertex(upStation);
            graph.addVertex(downStation);
            graph.addEdge(upStation, downStation, new DistanceLineEdge(section.getDistance(), line));
        }
    }

    private static Path generatePath(Station source, Station target, WeightedMultigraph<Station, DistanceLineEdge> graph) {
        try {
            GraphPath<Station, DistanceLineEdge> shortestPath = new DijkstraShortestPath<>(graph).getPath(source, target);
            return new Path(shortestPath);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("시작역 또는 종착역이 그래프에 존재하지 않습니다");
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("시작역에서 종착역으로 가는 최단 경로가 존재하지 않습니다");
        }
    }

    public List<Station> getStations() {
        return path.getVertexList();
    }

    public int getDistance() {
        return (int) path.getWeight();
    }

    public List<Line> getLines() {
        return path.getEdgeList()
                .stream()
                .map(DistanceLineEdge::getLine)
                .distinct()
                .collect(Collectors.toList());
    }
}
