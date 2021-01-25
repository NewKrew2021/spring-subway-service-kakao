package subway.path.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.station.domain.Station;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShortestPathExplorer {

    private final DijkstraShortestPath dijkstraShortestPath;

    public ShortestPathExplorer(SubwayGraph subwayGraph) {
        dijkstraShortestPath = new DijkstraShortestPath(subwayGraph.getGraph());
    }

    public Path exploreShortestPath(List<Line> lines, Station source, Station target) {
        List<Station> shortestPath = dijkstraShortestPath.getPath(source, target).getVertexList();
        int shortestDistance = (int) dijkstraShortestPath.getPathWeight(source, target);
        int maxExtraFareOfLine = getMaxExtraFareOfLine(shortestPath, lines);

        return new Path(shortestPath, shortestDistance, maxExtraFareOfLine);
    }

    private int getMaxExtraFareOfLine(List<Station> shortestPath, List<Line> lines) {
        Sections allSections = new Sections();
        lines.forEach(line -> allSections.addSections(line.getSections()));

        Set<Integer> extraFares = new HashSet<>();
        for (int i = 0; i < shortestPath.size() - 1; i++) {
            Section section = allSections.getSection(shortestPath.get(i), shortestPath.get(i + 1));
            extraFares.add(
                    lines.stream()
                    .filter(line -> line.getSections().hasSection(section))
                    .findFirst()
                    .get()
                    .getExtraFare()
            );
        }

        return Collections.max(extraFares);
    }
}
