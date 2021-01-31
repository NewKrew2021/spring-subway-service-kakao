package subway.path.domain;

import subway.line.domain.Line;
import subway.station.domain.Station;

import java.util.List;

public class PathVertex {
    private Station station;
    private List<Line> lineList;

    public PathVertex(Station station, List<Line> lineList) {
        this.station = station;
        this.lineList = lineList;
    }

    public Station getStation() {
        return station;
    }

    public List<Line> getLineList() {
        return lineList;
    }
}
