package subway.path.domain.path;

import subway.line.domain.Line;
import subway.path.domain.path.graph.SubwayGraphPath;
import subway.station.domain.Station;

import java.time.LocalDateTime;
import java.util.List;

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

    public static SubwayPath of(SubwayGraphPath path, LocalDateTime arrivalTime) {
        return new SubwayPath(path.getStations(), path.getTotalDistance(), path.getLines(), arrivalTime);
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
