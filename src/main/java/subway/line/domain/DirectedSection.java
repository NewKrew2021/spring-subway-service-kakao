package subway.line.domain;

import subway.station.domain.Station;

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
}
