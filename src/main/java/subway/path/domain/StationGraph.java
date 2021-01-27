package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.exception.custom.NoSuchPathException;
import subway.line.domain.Line;
import subway.path.domain.fare.AgeFareStrategy;
import subway.path.domain.fare.AgeFareStrategyFactory;
import subway.station.domain.Station;

import java.util.Comparator;
import java.util.List;

public class StationGraph {
    public static final int BASIC_FARE = 1250;
    private final WeightedMultigraph<Station, SectionEdge> graph = new WeightedMultigraph<>(SectionEdge.class);
    private final DijkstraShortestPath<Station, SectionEdge> dijkstraShortestPath;

    public StationGraph(List<Line> lines) {
        lines.forEach(this::addAllStationsOfLineAsVertex);
        lines.forEach(this::addAllSectionsOfLineAsEdge);
        dijkstraShortestPath = new DijkstraShortestPath<>(graph);
    }

    private void addAllStationsOfLineAsVertex(Line line) {
        line.getStations().forEach(graph::addVertex);
    }

    private void addAllSectionsOfLineAsEdge(Line line) {
        line.getSections().getSections().stream()
                .map(section -> new SectionEdge(section, line.getExtraFare()))
                .forEach(this::addEdgeToGraph);
    }

    private void addEdgeToGraph(SectionEdge sectionEdge) {
        graph.addEdge(sectionEdge.getSource(), sectionEdge.getTarget(), sectionEdge);
        graph.setEdgeWeight(sectionEdge, sectionEdge.getDistance());
    }

    public PathInfo getPathInfo(Station source, Station target, int age) {
        GraphPath<Station, SectionEdge> shortestPath = getShortestPath(source, target);

        int distance = (int) shortestPath.getWeight();
        int fare = calculateFare(distance, shortestPath.getEdgeList());

        AgeFareStrategy ageFareStrategy = AgeFareStrategyFactory.getInstance(age);
        return new PathInfo(shortestPath.getVertexList(), distance, ageFareStrategy.getDiscountedFare(fare));
    }

    private GraphPath<Station, SectionEdge> getShortestPath(Station source, Station target) {
        GraphPath<Station, SectionEdge> shortestPath = dijkstraShortestPath.getPath(source, target);
        if (shortestPath == null) {
            throw new NoSuchPathException();
        }
        return shortestPath;
    }

    private int calculateFare(int distance, List<SectionEdge> sectionEdgeList) {
        return calcDistanceFare(distance) + calcLineFare(sectionEdgeList);
    }

    private int calcLineFare(List<SectionEdge> sectionEdgeList) {
        return sectionEdgeList.stream()
                .map(SectionEdge::getExtraFare)
                .max(Comparator.naturalOrder())
                .orElse(0);
    }

    private int calcDistanceFare(int distance) {
        return BASIC_FARE +
                (int) Math.ceil(Math.min(Math.max((distance - 10), 0), 40) / 5.0) * 100 +
                (int) Math.ceil(Math.max((distance - 50), 0) / 8.0) * 100;
    }
}
