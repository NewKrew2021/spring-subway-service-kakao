package subway.path.domain.path;

import org.jgrapht.GraphPath;
import subway.line.domain.Line;
import subway.station.domain.Station;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SubwayPath {

    private final List<Station> stations;
    private final int distance;
    private final List<Line> lines;
    private final LocalDateTime arrivalTime;

    private SubwayPath(List<Station> stations, int distance, List<Line> lines, LocalDateTime arrivalTime) {
        this.stations = stations;
        this.distance = distance;
        this.lines = lines;
        this.arrivalTime = arrivalTime;
    }

    public static SubwayPath of(GraphPath<Station, SubwayEdge> path, LocalDateTime arrivalTime) {
        return new SubwayPath(
                path.getVertexList(),
                (int) path.getWeight(),
                getLines(path.getEdgeList()),
                arrivalTime
        );
    }

    private static List<Line> getLines(List<SubwayEdge> edges) {
        return edges.stream()
                .map(SubwayEdge::getLine)
                .collect(Collectors.toList());
    }

    public List<Station> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }

    public List<Line> getLines() {
        return lines;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
}
