package subway.line.domain;

import org.jgrapht.graph.DefaultWeightedEdge;
import subway.station.domain.Station;

public class Section extends DefaultWeightedEdge {
    protected Long id;
    protected Station upStation;
    protected Station downStation;
    protected int distance;

    public Section() {
    }

    public Section(Long id, Station upStation, Station downStation, int distance) {
        this.id = id;
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public Section(Station upStation, Station downStation, int distance) {
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public Station getUpStation() {
        return upStation;
    }

    public Station getDownStation() {
        return downStation;
    }

    public int getDistance() {
        return distance;
    }

    public boolean contains(Station station) {
        return upStation.equals(station) || downStation.equals(station);
    }
}
