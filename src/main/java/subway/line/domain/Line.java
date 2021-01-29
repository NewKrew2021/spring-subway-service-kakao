package subway.line.domain;

import subway.exception.WrongInputDataException;
import subway.station.domain.Station;

import java.util.List;

public class Line {
    public static final int MIN_EXTRA_FARE = 0;
    private final Long id;
    private final String name;
    private final String color;
    private final int extraFare;
    private final Sections sections;

    public Line(String name, String color) {
        this(null, name, color, new Sections(), MIN_EXTRA_FARE);
    }

    public Line(Long id, String name, String color) {
        this(id, name, color, new Sections(), MIN_EXTRA_FARE);
    }

    public Line(Long id, String name, String color, Sections sections) {
        this(id, name, color, sections, MIN_EXTRA_FARE);
    }

    public Line(String name, String color, int extraFare) {
        this(null, name, color, extraFare);
    }

    public Line(Long id, String name, String color, int extraFare) {
        this(id, name, color, new Sections(), extraFare);
    }

    public Line(Long id, String name, String color, Sections sections, int extraFare) {
        if (extraFare < MIN_EXTRA_FARE) {
            throw new WrongInputDataException("추가요금이 잘못 입력되었습니다.");
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
