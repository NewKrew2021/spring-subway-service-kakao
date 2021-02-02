package subway.line.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Lines {
    private final List<Line> lines;

    public Lines(List<Line> lines) {
        this.lines = lines;
    }

    public List<Line> getLines() {
        return lines;
    }

    public List getShortestPath(DijkstraShortestPath dijkstraShortestPath, Long source, Long target) {
        List shortestPath = dijkstraShortestPath.getPath(source, target).getVertexList();
        return shortestPath;
    }

    public DijkstraShortestPath getDijkstraShortestPath() {
        WeightedMultigraph<Long, DefaultWeightedEdge> graph
                = new WeightedMultigraph(DefaultWeightedEdge.class);

        lines.forEach(line -> line.getStations()
                .forEach(station -> graph.addVertex(station.getId())));

        lines.forEach(line -> line.getSections().getSections()
                .forEach(section -> graph.setEdgeWeight(graph.addEdge(section.getUpStation().getId(),
                        section.getDownStation().getId()), section.getDistance())));

        return new DijkstraShortestPath(graph);
    }

    public List<Section> getShortestPathSections(List<Long> shortestPath, List<Station> stations) {
        List<Section> sections = new ArrayList<>();
        Map<Long, Station> mapStations = stations.stream()
                .collect(Collectors.toMap(Station::getId, Function.identity()));
        for (int i = 0; i < shortestPath.size() - 1; i++) {
            sections.add(new Section(mapStations.get(shortestPath.get(i)), mapStations.get(shortestPath.get(i + 1))));
        }
        return sections;
    }
}
