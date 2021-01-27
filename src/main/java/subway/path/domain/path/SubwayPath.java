package subway.path.domain.path;

import org.jgrapht.GraphPath;
import subway.line.domain.Line;
import subway.path.domain.path.graph.PathAndArrival;
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

    public static SubwayPath from(PathAndArrival subwayPath) {
        GraphPath<Station, DistanceLineEdge> path = subwayPath.getPath();
        return new SubwayPath(
                path.getVertexList(),
                (int) path.getWeight(),
                getLines(path.getEdgeList()),
                subwayPath.getArrivalTime()
        );
    }

    private static List<Line> getLines(List<DistanceLineEdge> edges) {
        return edges.stream()
                .map(DistanceLineEdge::getLine)
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
