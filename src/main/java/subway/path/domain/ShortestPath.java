package subway.path.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;

public class ShortestPath {
    DijkstraShortestPath<Station, DefaultWeightedEdge> dijkstraShortestPath;
    List<Section> shortestSection;
    List<Station> shortestPathStations;

    public ShortestPath(List<Line> lines, Station source, Station target) {
        this.dijkstraShortestPath = getDijkstra(lines);
        this.shortestPathStations = setShortestPath(source, target);
        this.shortestSection = setShortestPathSections();
    }

    private DijkstraShortestPath<Station, DefaultWeightedEdge> getDijkstra(List<Line> lines) {
        WeightedMultigraph<Station, DefaultWeightedEdge> graph
                = new WeightedMultigraph<>(DefaultWeightedEdge.class);
        lines.forEach(line -> line.getStations()
                .forEach(graph::addVertex));
        lines.forEach(line -> line.getSections().getSections()
                .forEach(section -> graph.setEdgeWeight(graph.addEdge(section.getUpStation(),
                        section.getDownStation()), section.getDistance())));

        return new DijkstraShortestPath<>(graph);
    }

    private List<Station> setShortestPath(Station source, Station target) {
        return this.dijkstraShortestPath.getPath(source, target).getVertexList();
    }

    private List<Section> setShortestPathSections() {
        List<Section> sections = new ArrayList<>();
        for (int i = 0; i < shortestPathStations.size() - 1; i++) {
            sections.add(new Section(shortestPathStations.get(i), shortestPathStations.get(i + 1)));
        }
        return sections;
    }

    public List<Station> getShortestPathStations() {
        return shortestPathStations;
    }

    public List<Section> getShortestSection() {
        return shortestSection;
    }

    public int getTotalDistance() {
        return 0;
    }
}
