package subway.path.domain;

import subway.line.domain.Line;
import subway.station.domain.Station;

import java.util.List;
import java.util.Objects;

public class PathVertex {
    private Station station;

    public PathVertex(Station station) {
        this.station = station;
    }

    public Station getStation() {
        return station;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathVertex that = (PathVertex) o;
        return Objects.equals(station, that.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(station);
    }
}
