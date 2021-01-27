package subway.line.domain;

import subway.common.domain.Distance;
import subway.common.domain.Fare;
import subway.station.domain.Station;

import java.util.List;

public class Line {
    private final Long id;
    private final String name;
    private final String color;
    private final Fare extraFare;
    private final Sections sections;

    private Line(Long id, String name, String color, Fare extraFare, Sections sections) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.extraFare = extraFare;
        this.sections = sections;
    }

    private Line(Long id, String name, String color, Fare extraFare) {
        this(id, name, color, extraFare, new Sections());
    }

    public static Line of(Long id, String name, String color, Fare extraFare, Sections sections) {
        return new Line(id, name, color, extraFare, sections);
    }

    public static Line of(Long id, String name ,String color, Fare extraFare) {
        return new Line(id, name, color, extraFare);
    }

    public static Line of(String name, String color, Fare extraFare) {
        return new Line(null, name, color, extraFare);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Fare getExtraFare() {
        return extraFare;
    }

    public Sections getSections() {
        return sections;
    }

    public void addSection(Station upStation, Station downStation, Distance distance) {
        Section section = Section.of(id, upStation, downStation, distance);
        sections.addSection(section);
    }

    public void addSection(Section section) {
        if (section == null) {
            throw new IllegalArgumentException("비어있는 구간을 노선에 추가할 수 없습니다.");
        }
        sections.addSection(section);
    }

    public void removeSection(Station station) {
        sections.removeStation(station);
    }

    public List<Station> getStations() {
        return sections.getStations();
    }
}
