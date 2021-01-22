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
    public static List getShortestPath(DijkstraShortestPath dijkstraShortestPath, Long source, Long target) {
        List shortestPath = dijkstraShortestPath.getPath(source, target).getVertexList();
        return shortestPath;
    }

    public static DijkstraShortestPath getDijkstraShortestPath(List<Line> lines) {
        WeightedMultigraph<Long, DefaultWeightedEdge> graph
                = new WeightedMultigraph(DefaultWeightedEdge.class);

        lines.forEach(line -> line.getStations()
                .forEach(station -> graph.addVertex(station.getId())));

        lines.forEach(line -> line.getSections().getSections()
                .forEach(section -> graph.setEdgeWeight(graph.addEdge(section.getUpStation().getId(),
                        section.getDownStation().getId()), section.getDistance())));

        return new DijkstraShortestPath(graph);
    }

    public static List<Section> getShortestPathSections(List<Long> shortestPath, List<Station> stations) {
        List<Section> sections = new ArrayList<>();
        Map<Long, Station> mapStations = stations.stream()
                .collect(Collectors.toMap(Station::getId, Function.identity()));
        for (int i = 0; i < shortestPath.size() - 1; i++) {
            sections.add(new Section(mapStations.get(shortestPath.get(i)), mapStations.get(shortestPath.get(i + 1))));
        }
        return sections;
    }
}
