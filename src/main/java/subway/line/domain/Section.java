package subway.line.domain;

import org.jgrapht.graph.DefaultWeightedEdge;
import subway.station.domain.Station;

import java.util.Objects;

public class Section extends DefaultWeightedEdge {

    private Long id;
    private Long lindId;
    private Station upStation;
    private Station downStation;
    private int distance;

    public Section(Station upStation, Station downStation, int distance) {
        this(null, null, upStation, downStation, distance);
    }

    public Section(Long lineId, Station upStation, Station downStation, int distance) {
        this(null, lineId, upStation, downStation, distance);
    }

    public Section(Long id, Long lindId, Station upStation, Station downStation, int distance) {
        this.id = id;
        this.lindId = lindId;
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public Long getLindId() {
        return lindId;
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
        return distance == section.distance && Objects.equals(lindId, section.lindId) && Objects.equals(upStation, section.upStation) && Objects.equals(downStation, section.downStation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lindId, upStation, downStation, distance);
    }

    @Override
    protected double getWeight() {
        return getDistance();
    }

    @Override
    protected Object getSource() {
        return getUpStation();
    }

    @Override
    protected Object getTarget() {
        return getDownStation();
    }
}
