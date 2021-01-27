package subway.line.domain;

import subway.station.domain.Station;

import java.util.List;

public class Line {
    private Long id;
    private String name;
    private String color;
    private Sections sections;
    private int extraFare;

    public Line(String name, String color) {
        this(null, name, color, null, 0);
    }

    public Line(String name, String color, int extraFare) {
        this(null, name, color, null, extraFare);
    }

    public Line(Long id, String name, String color) {
        this(id, name, color, null, 0);
    }

    public Line(Long id, String name, String color, Sections sections) {
        this(id, name, color, sections, 0);
    }

    public Line(Long id, String name, String color, Sections sections, int extraFare) {
        if (sections == null) {
            sections = new Sections();
        }
        this.id = id;
        this.name = name;
        this.color = color;
        this.sections = sections;
        this.extraFare = extraFare;
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

    public Sections getSections() {
        return sections;
    }

    public int getExtraFare() {
        return extraFare;
    }

    public void addSection(Station upStation, Station downStation, int distance) {
        Section section = new Section(upStation, downStation, distance);
        sections.addSection(section);
    }

    public void addSection(Section section) {
        if (section == null) {
            return;
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
