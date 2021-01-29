package subway.line.domain;

import subway.exception.WrongInputDataException;
import subway.station.domain.Station;

import java.util.Objects;

public class Section {
    private static final int MIN_DISTANCE = 1;
    private final Long id;
    private final Station upStation;
    private final Station downStation;
    private final int distance;

    public Section(Long id, Station upStation, Station downStation, int distance) {
        if (upStation.equals(downStation)) {
            throw new WrongInputDataException("역이 잘못 입력되었습니다.");
        }
        if (distance < MIN_DISTANCE) {
            throw new WrongInputDataException("거리가 잘못 입력되었습니다.");
        }
        this.distance = distance;
        this.upStation = upStation;
        this.downStation = downStation;
        this.id = id;
    }

    public Section(Station upStation, Station downStation, int distance) {
        this(null, upStation, downStation, distance);
    }

    public Section(Station upStation, Station downStation) {
        this(null, upStation, downStation, MIN_DISTANCE);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return distance == section.distance && Objects.equals(upStation, section.upStation) && Objects.equals(downStation, section.downStation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(upStation, downStation, distance);
    }
}
