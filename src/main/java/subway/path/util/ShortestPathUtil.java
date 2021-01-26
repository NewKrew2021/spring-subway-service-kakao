package subway.util;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.Map;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ShortestPathUtil {
    public static List<Station> getShortestPath(DijkstraShortestPath<Station, DefaultWeightedEdge> dijkstraShortestPath, Station source, Station target) {
        return dijkstraShortestPath.getPath(source, target).getVertexList();
    }

    public static DijkstraShortestPath<Station, DefaultWeightedEdge> getDijkstraShortestPath(List<Line> lines) {
        WeightedMultigraph<Station, DefaultWeightedEdge> graph
                = new WeightedMultigraph<>(DefaultWeightedEdge.class);
        lines.forEach(line -> line.getStations()
                .forEach(station -> graph.addVertex(station)));
        lines.forEach(line -> line.getSections().getSections()
                .forEach(section -> graph.setEdgeWeight(graph.addEdge(section.getUpStation(),
                        section.getDownStation()), section.getDistance())));

        return new DijkstraShortestPath<>(graph);
    }

    public static List<Section> getShortestPathSections(List<Station> shortestPath) {
        List<Section> sections = new ArrayList<>();
        for (int i = 0; i < shortestPath.size() - 1; i++) {
            sections.add(new Section(shortestPath.get(i), shortestPath.get(i+1)));
        }
        return sections;
    }
}
