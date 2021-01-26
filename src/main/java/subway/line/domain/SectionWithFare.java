package subway.line.domain;

import subway.station.domain.Station;

import java.util.List;

public class SectionWithFare {
    private final int extraFare;
    private final Section section;

    public SectionWithFare(int extraFare, Section section) {
        this.extraFare = extraFare;
        this.section = section;
    }

    public List<Station> getStations() {
        return section.getStations();
    }

    public int getExtraFare() {
        return extraFare;
    }

    public Section getSection() {
        return section;
    }

    public Station getUpStation() {
        return section.getUpStation();
    }

    public Station getDownStation() {
        return section.getDownStation();
    }

    public int getDistance() {
        return section.getDistance();
    }
}
