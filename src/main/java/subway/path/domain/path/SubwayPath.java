package subway.path.domain.path;

import subway.line.domain.Line;
import subway.path.domain.path.graph.Path;
import subway.station.domain.Station;

import java.util.List;
import java.util.stream.Collectors;

public class SubwayPath {

    private final List<Station> stations;
    private final int distance;
    private final List<Line> lines;

    private SubwayPath(List<Station> stations, int distance, List<Line> lines) {
        this.stations = stations;
        this.distance = distance;
        this.lines = lines;
    }

    public static SubwayPath from(Path<Station, DistanceLineEdge> path) {
        return new SubwayPath(
                path.getVertexes(),
                (int) path.getTotalWeight(),
                getLines(path.getEdges())
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
}
