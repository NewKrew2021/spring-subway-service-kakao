package subway.path.dto;

import subway.path.domain.Path;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

public class PathResponse {
    private List<StationResponse> stations;
    private int distance;
    private int fare;

    public PathResponse() {
    }

    public PathResponse(PathResult pathResult){
        this.stations = pathResult.getPathVertices().getPathVertexList()
                .stream()
                .map(i -> new StationResponse(i.getStation().getId(), i.getStation().getName()))
                .collect(Collectors.toList());
        this.distance = pathResult.getDistance();
        this.fare = pathResult.getFare();
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
