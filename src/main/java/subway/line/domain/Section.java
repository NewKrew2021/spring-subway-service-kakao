package subway.line.domain;

import org.jgrapht.graph.DefaultWeightedEdge;
import subway.station.domain.Station;

public class Section extends DefaultWeightedEdge {
    private Long id;
    private Long lindId;
    private Station upStation;
    private Station downStation;
    private int distance;

    public Section() {
    }

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
