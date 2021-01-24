package subway.path.domain.path;

import org.jgrapht.GraphPath;
import subway.line.domain.Line;
import subway.station.domain.Station;

import java.util.List;
import java.util.stream.Collectors;

public class Path {

    private final List<Station> stations;
    private final int distance;
    private final List<Line> lines;

    private Path(List<Station> stations, int distance, List<Line> lines) {
        this.stations = stations;
        this.distance = distance;
        this.lines = lines;
    }

    public static Path from(GraphPath<Station, DistanceLineEdge> graphPath) {
        return new Path(
                graphPath.getVertexList(),
                (int) graphPath.getWeight(),
                getLines(graphPath)
        );
    }

    private static List<Line> getLines(GraphPath<Station, DistanceLineEdge> graphPath) {
        return graphPath.getEdgeList()
                .stream()
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
}
