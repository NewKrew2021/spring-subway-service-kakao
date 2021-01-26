package subway.path.domain;

import subway.station.domain.Station;

import java.util.List;

public class PathVertex {
    private Station station;
    private List<Long> lineList;

    public PathVertex(Station station, List<Long> lineList) {
        this.station = station;
        this.lineList = lineList;
    }

    public Station getStation() {
        return station;
    }

    public List<Long> getLineList() {
        return lineList;
    }
}
