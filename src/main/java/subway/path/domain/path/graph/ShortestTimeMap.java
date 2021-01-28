package subway.path.domain.path.graph;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.jgrapht.graph.Multigraph;
import subway.line.domain.Line;
import subway.path.domain.path.SubwayEdge;
import subway.path.domain.path.SubwayPath;
import subway.station.domain.Station;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

import static java.util.function.BinaryOperator.minBy;

public class ShortestTimeMap implements SubwayMap {

    private static final int FIRST_LINE_INDEX = 0;
    private static final int FIRST_STATION_INDEX = 0;
    private static final int NEXT = 1;
    private static final int ALL_PATH_COUNT = 1000;

    private final Graph<Station, SubwayEdge> graph;

    private ShortestTimeMap(Graph<Station, SubwayEdge> graph) {
        this.graph = graph;
    }

    public static ShortestTimeMap initialize(List<Line> lines) {
        return new ShortestTimeMap(GraphUtil.initializeGraph(new Multigraph<>(SubwayEdge.class), lines));
    }

    @Override
    public SubwayPath getPath(Station source, Station target, LocalDateTime departureTime) {
        GraphPath<Station, SubwayEdge> shortestPath =
                getShortestArrivalTimePath(new KShortestPaths<>(graph, ALL_PATH_COUNT).getPaths(source, target), departureTime);
        return SubwayPath.of(shortestPath, getArrivalTime(shortestPath, departureTime));
    }

    private GraphPath<Station, SubwayEdge> getShortestArrivalTimePath(List<GraphPath<Station, SubwayEdge>> graphPaths, LocalDateTime departureTime) {
        return graphPaths.stream()
                .reduce(minBy(arrivalTimeComparator(departureTime)))
                .orElseThrow(() -> new IllegalArgumentException("하나 이상의 경로는 반드시 존재해야 합니다"));
    }

    private Comparator<GraphPath<Station, SubwayEdge>> arrivalTimeComparator(LocalDateTime departureTime) {
        return Comparator.comparing(path -> getArrivalTime(path, departureTime));
    }

    private LocalDateTime getArrivalTime(GraphPath<Station, SubwayEdge> path, LocalDateTime departureTime) {
        List<SubwayEdge> sections = path.getEdgeList();
        List<Station> stations = path.getVertexList();

        LocalDateTime arrivalTime = getBoardingTime(departureTime, sections, stations);
        for (int i = 0; i < sections.size(); i++) {
            SubwayEdge section = sections.get(i);
            LocalDateTime nextArrivalTime = arrivalTime.plus(section.getDuration(), ChronoUnit.MINUTES);
            arrivalTime = getNextDepartureTime(stations.get(i + NEXT), nextArrivalTime, section.getLine());
        }
        return arrivalTime;
    }

    private LocalDateTime getBoardingTime(LocalDateTime departureTime, List<SubwayEdge> edges, List<Station> stations) {
        return getNextDepartureTime(
                stations.get(FIRST_STATION_INDEX),
                departureTime,
                edges.get(FIRST_LINE_INDEX).getLine()
        );
    }

    private LocalDateTime getNextDepartureTime(Station station, LocalDateTime baseDateTime, Line line) {
        return line.getDepartureTimesOf(station)
                .stream()
                .map(time -> LocalDateTime.of(baseDateTime.toLocalDate(), time))
                .filter(departureTime -> departureTime.isAfter(baseDateTime) || departureTime.isEqual(baseDateTime))
                .sorted()
                .findFirst()
                .orElse(LocalDateTime.MAX);
    }
}
