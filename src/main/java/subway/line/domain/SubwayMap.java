package subway.line.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.exception.LineNotFoundException;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SubwayMap {
    private List<Line> lines;

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
                .filter((line) -> line.getSections().hasStations(stationA,stationB)
                ).findFirst();

        if (!lineOptional.isPresent()) throw new LineNotFoundException();
        return lineOptional.get().getExtraFare();
    }

    public DirectedSections getShortestPath(Station source, Station destination) {
        List<Section> sections = findAllSections();
        DijkstraShortestPath<Station, DefaultWeightedEdge> graph = createGraph(sections);
        List<Station> orderedStations = graph.getPath(source, destination).getVertexList();
        List<DirectedSection> directedSections = new ArrayList<>();

        for (int i = 0; i < orderedStations.size() - 1; i++) {
            Station sourceStation = orderedStations.get(i);
            Station targetStation = orderedStations.get(i + 1);
            DirectedSection directedSection = new DirectedSection(findSection(sections, sourceStation, targetStation), sourceStation);
            directedSections.add(directedSection);
        }
        return new DirectedSections(directedSections,
                source,
                destination,
                graph,
                ExtraFare.of(this, directedSections));
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

    public void refresh(List<Line> lines) {
        this.lines = lines;
    }
}
