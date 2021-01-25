package subway.path.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.station.domain.Station;

import java.util.List;
import java.util.stream.Collectors;

public class SubwayPathFinder {
    private final DijkstraShortestPath<Station, DefaultWeightedEdge> graph;

    public SubwayPathFinder(List<Line> lines) {
        this.graph = crateGraph(lines);
    }

    public List<Station> getStationsBetween(Station sourceStation, Station targetStation) {
        return graph.getPath(sourceStation, targetStation).getVertexList();
    }

    private DijkstraShortestPath<Station, DefaultWeightedEdge> crateGraph(List<Line> lines) {
        List<Section> sections = lines.stream()
                .map(Line::getSections)
                .map(Sections::getSections)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        WeightedMultigraph<Station, DefaultWeightedEdge> graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);

        for (Section section : sections) {
            graph.addVertex(section.getUpStation());
            graph.addVertex(section.getDownStation());
            graph.setEdgeWeight(graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance());
        }

        return new DijkstraShortestPath<>(graph);
    }
}
