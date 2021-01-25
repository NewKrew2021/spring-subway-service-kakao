package subway.line.domain;

import subway.station.domain.Station;

import java.util.Objects;

public class DirectedSection extends Section{
    private final boolean isUpward;

    public DirectedSection(Section section, Station sourceStation) {
        super(section.getId(),
                section.getUpStation(),
                section.getDownStation(),
                section.getDistance());
        this.isUpward = section.getDownStation().equals(sourceStation);
    }

    public Station getSourceStation() {
        if(isUpward) {
            return getDownStation();
        }
        return getUpStation();
    }

    public Station getTargetStation() {
        if(isUpward) {
            return getUpStation();
        }
        return getDownStation();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectedSection that = (DirectedSection) o;
        return isUpward == that.isUpward;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isUpward);
    }

    @Override
    public String toString() {
        return "DirectedSection{" +
                "isUpward=" + isUpward +
                ", id=" + id +
                ", upStation=" + upStation +
                ", downStation=" + downStation +
                ", distance=" + distance +
                '}';
    }
}
