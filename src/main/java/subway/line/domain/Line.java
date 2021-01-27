package subway.line.domain;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.path.domain.Edge;
import subway.path.domain.Vertex;
import subway.station.domain.Station;

import java.util.List;

public class Line {
    public static final int DEFAULT_EXTRA_FARE = 0;
    public static final long DEFAULT_ID = 0L;
    private Long id;
    private String name;
    private String color;
    private int extraFare;
    private Sections sections;

    public Line() {
    }

    private Line(String name, String color) {
        this(DEFAULT_ID, name, color, DEFAULT_EXTRA_FARE, new Sections());
    }

    private Line(String name, String color, int extraFare) {
        this(DEFAULT_ID, name, color, extraFare, new Sections());
    }

    private Line(Long id, String name, String color, int extraFare) {
        this(id, name, color, extraFare, new Sections());
    }

    private Line(Long id, String name, String color, int extraFare, Sections sections) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.extraFare = extraFare;
        this.sections = sections;
    }

    public static Line of(String name, String color) {
        return new Line(name, color);
    }

    public static Line of(String name, String color, int extraFare) {
        return new Line(name, color, extraFare);
    }

    public static Line of(Long id, String name, String color, int extraFare) {
        return new Line(id, name, color, extraFare);
    }

    public static Line of(Long id, String name, String color, int extraFare, Sections sections) {
        return new Line(id, name, color, extraFare, sections);
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
        return this.sections;
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

    public void fillPath(WeightedMultigraph<Vertex, Edge> graph) {
        for (Section section : sections.getSections()) {
            Vertex v1 = Vertex.of(section.getUpStation().getId());
            Vertex v2 = Vertex.of(section.getDownStation().getId());
            graph.addVertex(v1);
            graph.addVertex(v2);
            graph.addEdge(v1, v2,new Edge(section.getDistance(),extraFare));
            graph.addEdge(v2, v1,new Edge(section.getDistance(),extraFare));
        }
    }
}
