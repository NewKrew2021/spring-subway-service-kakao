package subway.line.domain;

import subway.station.domain.Station;

public class DirectedSection extends Section {
    private final boolean isUpward;

    public DirectedSection(Section section, Station sourceStation) {
        super(section.id, section.upStation, section.downStation, section.distance);
        this.isUpward = section.downStation.equals(sourceStation);
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
}
