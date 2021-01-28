package subway.path.domain;

import subway.station.domain.Station;

import java.util.List;

public class PathVertex {
    private Station station;
    private List<Long> lineIdList;

    public PathVertex(Station station, List<Long> lineList) {
        this.station = station;
        this.lineIdList = lineList;
    }

    public Station getStation() {
        return station;
    }

    public List<Long> getLineIdList() {
        return lineIdList;
    }
}
