package subway.path.domain;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.line.domain.Sections;
import subway.station.domain.Station;

import java.util.List;

public class SubwayGraph {

    private final WeightedMultigraph<Station, DefaultWeightedEdge> graph;

    public SubwayGraph(List<Line> lines) {
        graph = new WeightedMultigraph(DefaultWeightedEdge.class);
        addStationVertex(lines);
        addSectionEdge(lines);
    }

    private void addStationVertex(List<Line> lines) {
        lines.stream()
                .flatMap(line -> line.getStations().stream())
                .distinct()
                .forEach(graph::addVertex);
    }

    private void addSectionEdge(List<Line> lines) {
        lines.forEach(line -> {
            Sections sections = line.getSections();
            sections.getSections()
                    .forEach(section -> graph.setEdgeWeight(
                            graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance())
                    );
        });
    }

    public WeightedMultigraph<Station, DefaultWeightedEdge> getGraph() {
        return graph;
    }
}
