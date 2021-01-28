package subway.path.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.line.domain.Sections;
import subway.station.domain.Station;

import java.util.List;

public class StationGraph {
    private WeightedMultigraph<Station, DefaultWeightedEdge> graph;

    public StationGraph(List<Line> lines) {
        graph = new WeightedMultigraph(DefaultWeightedEdge.class);
        addStationVertex(graph, lines);
        addSectionEdge(graph, lines);
    }

    private void addStationVertex(WeightedMultigraph<Station, DefaultWeightedEdge> graph, List<Line> lines) {
        lines.stream()
                .flatMap(line -> line.getStations().stream())
                .distinct()
                .forEach(graph::addVertex);
    }

    private void addSectionEdge(WeightedMultigraph<Station, DefaultWeightedEdge> graph, List<Line> lines) {
        lines.forEach(line -> {
            Sections sections = line.getSections();
            sections.getSections()
                    .forEach(section -> graph.setEdgeWeight(
                            graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance())
                    );
        });
    }

    public DijkstraShortestPath getDijkstraShortestPath() {
        return new DijkstraShortestPath(graph);
    }
}
