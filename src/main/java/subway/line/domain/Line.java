package subway.line.domain;

import subway.station.domain.Station;

import java.util.List;

public class Line {
    private Long id;
    private String name;
    private String color;
    private int fare;
    private Sections sections = new Sections();

    public Line() {
    }

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Line(String name, String color, int fare) {
        this.name = name;
        this.color = color;
        this.fare = fare;
    }

    public Line(Long id, String name, String color, int fare, Sections sections) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.fare = fare;
        this.sections = sections;
    }

    public Line(Long id, String name, String color, int fare) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.fare = fare;
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

    public int getFare() {
        return fare;
    }

    public void update(Line line) {
        this.name = line.getName();
        this.color = line.getColor();
    }

    public void addSection(Station upStation, Station downStation, long lineId, int distance) {
        Section section = new Section(upStation, downStation,lineId, distance);
        sections.addSection(section);
    }

    public void addSection(Section section) {
        if (section == null) {
            return;
        }
        sections.addSection(section);
    }

    public void removeSection(Line line, Station station) {
        sections.removeStation(line, station);
    }

    public List<Station> getStations() {
        return sections.getStations();
    }
}
