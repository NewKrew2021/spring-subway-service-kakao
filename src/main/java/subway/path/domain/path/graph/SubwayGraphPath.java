package subway.path.domain.path.graph;

import org.jgrapht.GraphPath;
import subway.line.domain.Line;
import subway.path.domain.path.SubwayEdge;
import subway.station.domain.Station;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SubwayGraphPath {

    private static final int FIRST_LINE_INDEX = 0;
    private static final int FIRST_STATION_INDEX = 0;
    private static final int NEXT = 1;

    private final GraphPath<Station, SubwayEdge> path;

    public SubwayGraphPath(GraphPath<Station, SubwayEdge> path) {
        this.path = path;
    }

    public Optional<LocalDateTime> findArrivalTime(LocalDateTime departureTime) {
        List<SubwayEdge> sections = path.getEdgeList();
        List<Station> stations = path.getVertexList();

        Optional<LocalDateTime> arrivalTime = findBoardingTime(departureTime, sections, stations);
        for (int i = 0; i < sections.size(); i++) {
            SubwayEdge section = sections.get(i);
            Station nextStation = stations.get(i + NEXT);
            arrivalTime = arrivalTime.flatMap(time -> findNextStationDepartureTime(nextStation, time, section));
        }
        return arrivalTime;
    }

    private Optional<LocalDateTime> findBoardingTime(LocalDateTime baseDateTime, List<SubwayEdge> edges, List<Station> stations) {
        return findDepartureTime(
                stations.get(FIRST_STATION_INDEX),
                baseDateTime,
                edges.get(FIRST_LINE_INDEX).getLine()
        );
    }

    private Optional<LocalDateTime> findNextStationDepartureTime(Station nextStation, LocalDateTime time, SubwayEdge section) {
        return findDepartureTime(
                nextStation,
                time.plus(section.getDuration(), ChronoUnit.MINUTES),
                section.getLine()
        );
    }

    private Optional<LocalDateTime> findDepartureTime(Station station, LocalDateTime baseDateTime, Line line) {
        return line.getDepartureTimesOf(station)
                .stream()
                .map(time -> LocalDateTime.of(baseDateTime.toLocalDate(), time))
                .filter(departureTime -> departureTime.isAfter(baseDateTime) || departureTime.isEqual(baseDateTime))
                .sorted()
                .findFirst();
    }

    public List<Station> getStations() {
        return path.getVertexList();
    }

    public int getTotalDistance() {
        return (int) path.getWeight();
    }

    public List<Line> getLines() {
        return path.getEdgeList()
                .stream()
                .map(SubwayEdge::getLine)
                .collect(Collectors.toList());
    }
}
