package subway.path.domain;

import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;


public class SubwayMap {
    private final List<Line> lines;
    private final SubwayPathFinder subwayPathFinder;

    public SubwayMap(List<Line> lines) {
        this.lines = lines;
        this.subwayPathFinder = new SubwayPathFinder(lines);
    }

    public DirectedSections getShortestPath(Station source, Station destination) {
        List<Station> orderedStations = subwayPathFinder.getStationsBetween(source, destination);

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
