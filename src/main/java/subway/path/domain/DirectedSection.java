package subway.path.domain;

import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

public class DirectedSection extends Section {
    private final Line line;
    private final boolean isUpward;

    public DirectedSection(Line line, Section section, Station sourceStation) {
        super(section.getId(), section.getUpStation(), section.getDownStation(), section.getDistance());
        this.line = line;
        this.isUpward = section.getDownStation().equals(sourceStation);
    }

    public Line getLine() {
        return line;
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
