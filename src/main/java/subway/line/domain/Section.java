package subway.line.domain;

import subway.station.domain.Station;

public class Section {
    private Long id;
    private Station upStation;
    private Station downStation;
    private int distance;

    public Section() {
    }

    public Section(Long id, Station upStation, Station downStation, int distance) {
        this(upStation, downStation, distance);
        this.id = id;
    }

    public Section(Station upStation, Station downStation, int distance) {
        this(upStation, downStation);
        this.distance = distance;
    }

    public Section(Station upStation, Station downStation) {
        this.upStation = upStation;
        this.downStation = downStation;
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
}
