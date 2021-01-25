package subway.path.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;


public class SubwayNavigator {
    private final List<Line> lines;
    private final DijkstraShortestPath<Station, DefaultWeightedEdge> subwayMap;

    public SubwayNavigator(List<Line> lines) {
        this.lines = lines;
        this.subwayMap = SubwayMap.of(lines);
    }

    public DirectedSections getShortestPath(Station source, Station destination) {
        List<Station> orderedStations = subwayMap.getPath(source, destination).getVertexList();

        List<DirectedSection> directedSections = new ArrayList<>();
        for (int i = 0; i < orderedStations.size() - 1; i++) {
            Station sourceStation = orderedStations.get(i);
            Station targetStation = orderedStations.get(i + 1);
            directedSections.add(findDirectedSection(sourceStation, targetStation));
        }

        return new DirectedSections(directedSections);
    }

    private DirectedSection findDirectedSection(Station sourceStation, Station targetStation) {
        Section containingSection = findSection(sourceStation, targetStation);
        return new DirectedSection(findLine(containingSection), containingSection, sourceStation);
    }

    private Line findLine(Section section) {
        return this.lines.stream()
                .filter(line -> line.getSections().getSections().contains(section))
                .findAny()
                .orElseThrow(RuntimeException::new);
    }

    private Section findSection(Station sourceStation, Station targetStation){
        return this.lines.stream()
                .map(Line::getSections)
                .map(Sections::getSections)
                .flatMap(List::stream)
                .filter(section -> section.contains(sourceStation) && section.contains(targetStation))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
