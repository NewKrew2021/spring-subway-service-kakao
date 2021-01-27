package subway.line.domain;

import subway.station.domain.Station;

import java.util.List;

public class Line {
    private final Long id;
    private String name;
    private String color;
    private Sections sections;
    private int extraFare;

    public Line(Long id, String name, String color, int extraFare) {
        this(id, name, color, new Sections(), extraFare);
    }

    public Line(Long id, String name, String color, Sections sections, int extraFare) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.sections = sections;
        this.extraFare = extraFare;
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

    public void changeToNewAttributes(String name, String color, int extraFare) {
        this.name = name;
        this.color = color;
        this.extraFare = extraFare;
    }

    public List<Station> getStations() {
        return sections.getStations();
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
}
