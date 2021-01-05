package subway.map.dto;

import subway.line.dto.LineResponse;
import subway.station.dto.StationResponse;

import java.util.List;

public class MapResponse {
    private List<StationResponse> stations;
    private List<LineResponse> lines;

    public MapResponse(List<StationResponse> stations, List<LineResponse> lines) {
        this.stations = stations;
        this.lines = lines;
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public List<LineResponse> getLines() {
        return lines;
    }
}
