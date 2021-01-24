package subway.path.domain.path;

import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.List;

public class SubwayGraph {

    private final ShortestPathAlgorithm<Station, DistanceLineEdge> graph;

    private SubwayGraph(ShortestPathAlgorithm<Station, DistanceLineEdge> graph) {
        this.graph = graph;
    }

    public static SubwayGraph from(List<Line> lines) {
        WeightedMultigraph<Station, DistanceLineEdge> graph = new WeightedMultigraph<>(DistanceLineEdge.class);
        for (Line line : lines) {
            generateGraph(graph, line);
        }

        return new SubwayGraph(new DijkstraShortestPath<>(graph));
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

    public Path getPath(Station source, Station target) {
        try {
            return Path.from(graph.getPath(source, target));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("시작역 또는 종착역이 그래프에 존재하지 않습니다");
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("시작역에서 종착역으로 가는 최단 경로가 존재하지 않습니다");
        }
    }
}
