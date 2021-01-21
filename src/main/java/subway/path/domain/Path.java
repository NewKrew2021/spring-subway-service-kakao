package subway.path.domain;

import subway.path.dto.PathResponse;
import subway.station.domain.Station;

import java.util.List;
import java.util.stream.Collectors;

public class Path {
    private final List<Station> path;
    private final int distance;

    public Path(List<Station> path, int distance) {
        this.path = path;
        this.distance = distance;
    }

    public PathResponse toResponse() {
        return new PathResponse(path.stream()
                .map(Station::toResponse)
                .collect(Collectors.toList()), distance, 0);
    }

    public List<Station> getPath() {
        return path;
    }

    public int getDistance() {
        return distance;
    }
}
