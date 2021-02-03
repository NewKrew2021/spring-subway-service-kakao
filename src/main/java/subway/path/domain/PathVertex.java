package subway.path.domain;

import subway.line.domain.Line;
import subway.station.domain.Station;

import java.util.List;

public class PathVertex {
    private Station station;

    public PathVertex(Station station) {
        this.station = station;
    }

    public Station getStation() {
        return station;
    }
}
