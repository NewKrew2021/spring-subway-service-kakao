package subway.line.domain;

import subway.station.domain.Station;

import java.util.Objects;

public class DirectedSection {
    private final Station upStation;
    private final Station downStation;
    private final boolean isUpward;

    public DirectedSection(Section section, Station sourceStation) {
        this.upStation = section.getUpStation();
        this.downStation = section.getDownStation();
        this.isUpward = section.getDownStation().equals(sourceStation);
    }

    public Station getSourceStation() {
        if(isUpward) {
            return downStation;
        }
        return upStation;
    }

    public Station getTargetStation() {
        if(isUpward) {
            return upStation;
        }
        return downStation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectedSection that = (DirectedSection) o;
        return isUpward == that.isUpward && Objects.equals(upStation, that.upStation) && Objects.equals(downStation, that.downStation);
    }

    public Station getUpStation() {
        return upStation;
    }

    public Station getDownStation() {
        return downStation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isUpward);
    }

    @Override
    public String toString() {
        return "DirectedSection{" +
                "isUpward=" + isUpward +
                ", upStation=" + upStation +
                ", downStation=" + downStation +
                '}';
    }
}
