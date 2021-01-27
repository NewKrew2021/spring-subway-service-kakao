package subway.line.domain;

import subway.station.domain.Station;

import java.util.List;

public class Line {
    private final Long id;
    private final int extraFare;
    private final Sections sections;

    private String name;
    private String color;

    public Line(String name, String color) {
        this(null, name, color, 0, null);
    }

    public Line(String name, String color, int extraFare) {
        this(null, name, color, extraFare, null);
    }

    public Line(Long id, String name, String color) {
        this(id, name, color, 0, null);
    }

    public Line(Long id, String name, String color, Sections sections) {
        this(id, name, color, 0, sections);
    }

    public Line(Long id, String name, String color, int extraFare, Sections sections) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.extraFare = extraFare;
        this.sections = sections == null ? new Sections() : sections;
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

    public void update(Line line) {
        this.name = line.getName();
        this.color = line.getColor();
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
