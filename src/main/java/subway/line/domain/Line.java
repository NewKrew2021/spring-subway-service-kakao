package subway.line.domain;

import subway.station.domain.Station;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Line {
    private Long id;
    private String name;
    private String color;
    private int extraFare;
    private LocalTime startTime;
    private LocalTime endTime;
    private int timeInterval; // unit - minute
    private Sections sections = new Sections();

    public Line() {
    }

    public Line(String name, String color, int extraFare, LocalTime startTime, LocalTime endTime, int timeInterval) {
        this.name = name;
        this.color = color;
        this.extraFare = extraFare;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeInterval = timeInterval;
    }

    public Line(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Line(Long id, String name, String color, int extraFare, LocalTime startTime, LocalTime endTime, int timeInterval, Sections sections) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.extraFare = extraFare;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeInterval = timeInterval;
        this.sections = sections;
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

    public LocalDateTime getNextDepartureTimeOf(LocalDateTime dateTime) {
        LocalDateTime departureTime = LocalDateTime.of(dateTime.toLocalDate(), startTime);
        while (departureTime.isBefore(dateTime)) {
            departureTime = departureTime.plus(timeInterval, ChronoUnit.MINUTES);
        }

        requireBeforeEndTime(dateTime, departureTime);
        return departureTime;
    }

    private void requireBeforeEndTime(LocalDateTime dateTime, LocalDateTime departureTime) {
        if (departureTime.isAfter(LocalDateTime.of(dateTime.toLocalDate(), endTime))) {
            throw new IllegalArgumentException("이 시간 이후로 출발하는 열차가 존재하지 않습니다");
        }
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

    public int getExtraFare() {
        return extraFare;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public List<Section> getSections() {
        return sections.getSections();
    }
}
