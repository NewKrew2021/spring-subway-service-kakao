package subway.path.domain.path.time;

import org.jgrapht.GraphPath;
import subway.line.domain.Line;
import subway.path.domain.path.SubwayEdge;
import subway.path.domain.path.graph.PathAndArrival;
import subway.station.domain.Station;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

import static java.util.function.BinaryOperator.minBy;

public class ShortestTimePathFinder {

    private static final int FIRST_LINE_INDEX = 0;
    private static final int FIRST_STATION_INDEX = 0;
    private static final int NEXT = 1;

    private final List<GraphPath<Station, SubwayEdge>> graphPaths;

    ShortestTimePathFinder(List<GraphPath<Station, SubwayEdge>> graphPaths) {
        requireNotEmpty(graphPaths);

        this.graphPaths = graphPaths;
    }

    private void requireNotEmpty(List<GraphPath<Station, SubwayEdge>> graphPaths) {
        if (graphPaths == null || graphPaths.isEmpty()) {
            throw new IllegalArgumentException("하나 이상의 경로가 존재해야합니다");
        }
    }

    public PathAndArrival getPath(LocalDateTime departureTime) {
        GraphPath<Station, SubwayEdge> shortestPath = getShortestArrivalTimePath(departureTime);

        return new PathAndArrival(shortestPath, getArrivalTime(shortestPath, departureTime));
    }

    private GraphPath<Station, SubwayEdge> getShortestArrivalTimePath(LocalDateTime departureTime) {
        return graphPaths.stream()
                .reduce(minBy(arrivalTimeComparator(departureTime)))
                .orElseThrow(() -> new AssertionError("하나 이상의 경로는 반드시 존재합니다"));
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
