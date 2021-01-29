package subway.line.domain;

import subway.station.domain.Station;

import java.util.List;

public class ResultPath {

    private final ExtraFare maxExtraFare;
    private final int distance;
    private final List<Station> stations;

    public ResultPath(List<Station> stations, int distance, ExtraFare maxExtraFare) {
        this.stations = stations;
        this.maxExtraFare = maxExtraFare;
        this.distance = distance;
    }

    public ExtraFare getMaxExtraFare() {
        return maxExtraFare;
    }

    public int getDistance() {
        return distance;
    }

    public List<Station> getStations(){
        return stations;
    }
}
