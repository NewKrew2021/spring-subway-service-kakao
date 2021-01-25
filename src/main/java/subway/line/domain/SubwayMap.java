package subway.line.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SubwayMap {
    private final List<Line> lines;

    public SubwayMap(List<Line> lines) {
        this.lines = lines;
    }

    public List<Section> findAllSections() {
        return this.lines.stream()
                .map(Line::getSections)
                .map(Sections::getSections)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public int getExtraFare(Station stationA, Station stationB) {
        Optional<Line> lineOptional = lines.stream()
                .filter((line) -> !line.getSections().getSections().stream()
                        .filter(section -> section.contains(stationA) && section.contains(stationB))
                        .findAny().equals(Optional.empty())
                ).findFirst();

        if (!lineOptional.isPresent()) throw new RuntimeException();

        return lineOptional.get().getExtraFare();
    }

    public DirectedSections getShortestPath(Station source, Station destination) {
        List<Section> sections = findAllSections();
        List<Station> orderedStations = createGraph(sections).getPath(source, destination).getVertexList();
        List<DirectedSection> directedSections = new ArrayList<>();

        int maxFare = Integer.MIN_VALUE;
        for (int i = 0; i < orderedStations.size() - 1; i++) {
            Station sourceStation = orderedStations.get(i);
            Station targetStation = orderedStations.get(i + 1);
            DirectedSection directedSection = new DirectedSection(findSection(sections, sourceStation, targetStation), sourceStation);
            maxFare = Math.max(maxFare, getExtraFare(sourceStation, targetStation));
            directedSections.add(directedSection);
        }
        return new DirectedSections(directedSections, maxFare);
    }

    private DijkstraShortestPath<Station, DefaultWeightedEdge> createGraph(List<Section> sections) {
        WeightedMultigraph<Station, DefaultWeightedEdge> graph =
                new WeightedMultigraph<>(DefaultWeightedEdge.class);

        for (Section section : sections) {
            graph.addVertex(section.getUpStation());
            graph.addVertex(section.getDownStation());
            graph.setEdgeWeight(graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance());
        }

        return new DijkstraShortestPath<>(graph);
    }

    private Section findSection(List<Section> sections, Station stationA, Station stationB) {
        return sections.stream()
                .filter(section -> section.contains(stationA) && section.contains(stationB))
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

}
