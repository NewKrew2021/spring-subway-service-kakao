package subway.line.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.exception.LineNotFoundException;
import subway.path.domain.ResultPath;
import subway.station.domain.Station;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SubwayMap {
    private List<Line> lines;

    public SubwayMap(List<Line> lines) {
        this.lines = lines;
    }

    public ResultPath calculateShortestPath(Station source, Station destination) {
        GraphPath<Station, Section> path = createGraph(flatLineToSections()).getPath(source, destination);
        int extraFare =  path.getEdgeList().stream()
                .mapToInt(section -> getExtraFare(
                        section.getUpStation(),
                        section.getDownStation()
                )).max().orElseThrow(LineNotFoundException::new);
        return new ResultPath(
                path.getVertexList(),
                (int) path.getWeight(),
                ExtraFare.of(extraFare));
    }

    private List<Section> flatLineToSections() {
        return this.lines.stream()
                .map(Line::getSections)
                .map(Sections::getSections)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private int getExtraFare(Station stationA, Station stationB) {
        Optional<Line> lineOptional = lines.stream()
                .filter((line) -> line.getSections().hasStations(stationA, stationB)
                ).findFirst();

        if (!lineOptional.isPresent()) throw new LineNotFoundException();
        return lineOptional.get().getExtraFare();
    }

    private DijkstraShortestPath<Station, Section> createGraph(List<Section> sections) {
        WeightedMultigraph<Station, Section> graph =
                new WeightedMultigraph<>(Section.class);
        sections.forEach(section -> {
            graph.addVertex(section.getUpStation());
            graph.addVertex(section.getDownStation());
            graph.addEdge(section.getUpStation(), section.getDownStation(), section);
            graph.setEdgeWeight(section, section.getDistance());
        });
        return new DijkstraShortestPath<>(graph);
    }

    public void refresh(List<Line> lines) {
        this.lines = lines;
    }
}
