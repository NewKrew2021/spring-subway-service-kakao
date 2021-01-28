package subway.line.domain;

import org.jgrapht.graph.DefaultWeightedEdge;
import subway.station.domain.Station;

import java.util.Objects;

public class Section extends DefaultWeightedEdge {
    private Long id;
    private Station upStation;
    private Station downStation;
    private Long lineId;
    private int distance;

    public Section() {
    }

    public Section(Long id, Station upStation, Station downStation, long lineId, int distance) {
        this.id = id;
        this.upStation = upStation;
        this.downStation = downStation;
        this.lineId = lineId;
        this.distance = distance;
    }

    public Section(Station upStation, Station downStation, long lineId, int distance) {
        this(null, upStation, downStation, lineId, distance);
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

    public Long getLineId() {
        return lineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return distance == section.distance &&
                Objects.equals(id, section.id) &&
                Objects.equals(upStation, section.upStation) &&
                Objects.equals(downStation, section.downStation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, upStation, downStation, distance);
    }

    @Override
    protected Object getSource() {
        return upStation;
    }

    @Override
    protected Object getTarget() {
        return downStation;
    }

    @Override
    protected double getWeight() {
        return distance;
    }
}
