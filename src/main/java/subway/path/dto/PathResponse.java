package subway.path.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import subway.path.domain.Path;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

public class PathResponse {
    private final List<StationResponse> stations;
    private final int distance;
    private final int fare;

    @JsonCreator
    public PathResponse(List<StationResponse> stations, int distance, int fare) {
        this.stations = stations;
        this.distance = distance;
        this.fare = fare;
    }

    public static PathResponse of(Path path, int fare) {
        return new PathResponse(path.getPath().stream()
                .map(Station::toResponse)
                .collect(Collectors.toList()), path.getDistance(), fare);
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }

    public int getFare() {
        return fare;
    }
}
