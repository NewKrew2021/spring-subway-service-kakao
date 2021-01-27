package subway.line.domain;

import subway.common.domain.Distance;
import subway.station.domain.Station;

import java.util.Arrays;
import java.util.List;

public class Section {
    private final Long id;
    private final Station upStation;
    private final Station downStation;
    private final Distance distance;

    private Section(Long id, Station upStation, Station downStation, Distance distance) {
        validate(upStation, downStation);
        this.id = id;
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    private void validate(Station upStation, Station downStation) {
        if (upStation.equals(downStation)) {
            throw new IllegalArgumentException("상행 종점과 하행 종점이 동일한 노선은 생성할 수 없습니다.");
        }
    }

    public static Section of(Long id, Station upStation, Station downStation, Distance distance) {
        return new Section(id, upStation, downStation, distance);
    }

    public static Section of(Station upStation, Station downStation, Distance distance) {
        return new Section(null, upStation, downStation, distance);
    }

    public static Section of(Long id, Section section) {
        return new Section(id, section.upStation, section.downStation, section.distance);
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

    public Distance getDistance() {
        return distance;
    }

    public List<Station> getStations() {
        return Arrays.asList(upStation, downStation);
    }

    public boolean isShorter(Section section) {
        return distance.isShorter(section.distance);
    }

    public Distance getDifferenceOfDistance(Section section) {
        return distance.getDifference(section.distance);
    }

    public Distance getSumOfDistance(Section section) {
        return distance.getSum(section.distance);
    }
}
