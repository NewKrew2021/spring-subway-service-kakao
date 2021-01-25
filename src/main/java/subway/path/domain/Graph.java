package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.path.domain.fare.AgeFareStrategy;
import subway.path.domain.fare.AgeFareStrategyFactory;
import subway.station.domain.Station;

import java.util.Comparator;
import java.util.List;

public class Graph {
    public static final int BASIC_FARE = 1250;
    private final WeightedMultigraph<Station, Edge> graph = new WeightedMultigraph<>(Edge.class);
    private final DijkstraShortestPath<Station, Edge> dijkstraShortestPath;

    public Graph(List<Line> lines) {
        lines.stream()
                .map(Line::getStations)
                .flatMap(List::stream)
                .distinct().forEach(graph::addVertex);
        lines.forEach(this::addAllSectionsOfLineAsEdge);

        dijkstraShortestPath = new DijkstraShortestPath<>(graph);
    }

    private void addAllSectionsOfLineAsEdge(Line line) {
        line.getSections().getSections().stream()
                .map(section -> new Edge(section.getUpStation(), section.getDownStation(),
                        line.getExtraFare(), section.getDistance()))
                .forEach(this::addEdgeToGraph);
    }

    private void addEdgeToGraph(Edge edge) {
        graph.addEdge(edge.getSource(), edge.getTarget(), edge);
        graph.setEdgeWeight(edge, edge.getDistance());
    }

    public Path getPathInfo(Station source, Station target, int age) {
        GraphPath<Station, Edge> shortestPath = getShortestPath(source, target);

        int distance = (int) shortestPath.getWeight();
        int fare = calculateFare(distance, shortestPath.getEdgeList());
        AgeFareStrategy ageFareStrategy = AgeFareStrategyFactory.getInstance(age);

        return new Path(shortestPath.getVertexList(), distance, ageFareStrategy.getDiscountedFare(fare));
    }

    private GraphPath<Station, Edge> getShortestPath(Station source, Station target) {
        GraphPath<Station, Edge> shortestPath = dijkstraShortestPath.getPath(source, target);
        if (shortestPath == null) {
            throw new RuntimeException("경로가 없습니다.");
        }
        return shortestPath;
    }

    private int calculateFare(int distance, List<Edge> edgeList) {
        return calcDistanceFare(distance) + calcLineFare(edgeList);
    }

    private int calcLineFare(List<Edge> edgeList) {
        return edgeList.stream().map(Edge::getExtraFare).max(Comparator.naturalOrder()).orElse(0);
    }

    private int calcDistanceFare(int distance) {
        return BASIC_FARE +
                (int) Math.ceil(Math.min(Math.max((distance - 10), 0), 40) / 5.0) * 100 +
                (int) Math.ceil(Math.max((distance - 50), 0) / 8.0) * 100;
    }
}
