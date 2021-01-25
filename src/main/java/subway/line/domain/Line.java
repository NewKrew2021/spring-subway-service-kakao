package subway.line.domain;

import subway.exception.WrongInputDataException;
import subway.station.domain.Station;

import java.util.List;

public class Line {
    private Long id;
    private String name;
    private String color;
    private int extraFare;
    private Sections sections = new Sections();

    public Line() {
    }

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Line(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Line(Long id, String name, String color, Sections sections) {
        this(id, name, color);
        this.sections = sections;
    }

    public Line(String name, String color, int extraFare) {
        this(name, color);
        if(extraFare < 0){
            throw new WrongInputDataException("추가요금이 잘못 입력되었습니다.");
        }
        this.extraFare = extraFare;
    }

    public Line(Long id, String name, String color, int extraFare) {
        this(name, color, extraFare);
        this.id = id;
    }

    public Line(Long id, String name, String color, Sections sections, int extraFare) {
        this(id, name, color, extraFare);
        this.sections = sections;
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

    public int getExtraFare() {
        return extraFare;
    }

    public Sections getSections() {
        return sections;
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
