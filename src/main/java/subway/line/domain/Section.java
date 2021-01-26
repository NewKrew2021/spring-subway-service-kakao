package subway.line.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import subway.station.domain.Station;

public class Section {
    private final Long id;
    private final Long lineId;
    private final Station upStation;
    private final Station downStation;
    private final int distance;

    public Section(Station upStation, Station downStation, int distance) {
        this(0L, 0L, upStation, downStation, distance);
    }

    public Section(Long lineId, Station upStation, Station downStation, int distance) {
        this(0L, lineId, upStation, downStation, distance);
    }

    @JsonCreator
    public Section(Long id, Long lineId, Station upStation, Station downStation, int distance) {
        this.id = id;
        this.lineId = lineId;
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
}
