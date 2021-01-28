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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        return getShortestPath(getAllPathsWithArrival(source, target, departureTime));
    }

    private SubwayPath getShortestPath(Map<GraphPath<Station, SubwayEdge>, LocalDateTime> pathWithArrivalTime) {
        return pathWithArrivalTime.entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .map(entry -> SubwayPath.of(entry.getKey(), entry.getValue()))
                .orElseThrow(() -> new IllegalArgumentException("당일 도착하는 경로가 존재하지 않습니다"));
    }

    private Map<GraphPath<Station, SubwayEdge>, LocalDateTime> getAllPathsWithArrival(Station source, Station target, LocalDateTime departureTime) {
        Map<GraphPath<Station, SubwayEdge>, LocalDateTime> pathsWithArrival = new HashMap<>();

        for (GraphPath<Station, SubwayEdge> path : getAllPaths(source, target)) {
            findArrivalTime(path, departureTime)
                    .ifPresent(arrivalTime -> pathsWithArrival.put(path, arrivalTime));
        }
        return pathsWithArrival;
    }

    private List<GraphPath<Station, SubwayEdge>> getAllPaths(Station source, Station target) {
        try {
            return new KShortestPaths<>(graph, ALL_PATH_COUNT).getPaths(source, target);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("시작역 또는 종착역이 그래프에 존재하지 않습니다");
        }
    }

    private Optional<LocalDateTime> findArrivalTime(GraphPath<Station, SubwayEdge> path, LocalDateTime departureTime) {
        List<SubwayEdge> sections = path.getEdgeList();
        List<Station> stations = path.getVertexList();

        Optional<LocalDateTime> arrivalTime = findBoardingTime(departureTime, sections, stations);
        for (int i = 0; i < sections.size(); i++) {
            SubwayEdge section = sections.get(i);
            Station nextStation = stations.get(i + NEXT);
            arrivalTime = arrivalTime.flatMap(time -> getNextDepartureTime(time, section, nextStation));
        }
        return arrivalTime;
    }

    private Optional<LocalDateTime> findBoardingTime(LocalDateTime departureTime, List<SubwayEdge> edges, List<Station> stations) {
        return findNextDepartureTime(
                stations.get(FIRST_STATION_INDEX),
                departureTime,
                edges.get(FIRST_LINE_INDEX).getLine()
        );
    }

    private Optional<LocalDateTime> getNextDepartureTime(LocalDateTime time, SubwayEdge section, Station nextStation) {
        return findNextDepartureTime(
                nextStation,
                time.plus(section.getDuration(), ChronoUnit.MINUTES),
                section.getLine()
        );
    }

    private Optional<LocalDateTime> findNextDepartureTime(Station station, LocalDateTime baseDateTime, Line line) {
        return line.getDepartureTimesOf(station)
                .stream()
                .map(time -> LocalDateTime.of(baseDateTime.toLocalDate(), time))
                .filter(departureTime -> departureTime.isAfter(baseDateTime) || departureTime.isEqual(baseDateTime))
                .sorted()
                .findFirst();
    }
}
