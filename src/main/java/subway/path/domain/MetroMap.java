package subway.path.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MetroMap {
    DijkstraShortestPath<Station, DefaultWeightedEdge> dijkstraShortestPath;

    public MetroMap() {
    }

    public MetroMap(List<Line> lines) {
        this.dijkstraShortestPath = getDijkstra(lines);
    }

    private DijkstraShortestPath<Station, DefaultWeightedEdge> getDijkstra(List<Line> lines) {
        WeightedMultigraph<Station, DefaultWeightedEdge> graph
                = new WeightedMultigraph<>(DefaultWeightedEdge.class);
        lines.forEach(line -> line.getStations()
                .forEach(graph::addVertex));
        lines.forEach(line -> line.getSections().getSections()
                .forEach(section -> graph.setEdgeWeight(graph.addEdge(section.getUpStation(),
                        section.getDownStation()), section.getDistance())));

        return new DijkstraShortestPath<>(graph);
    }

    public List<Station> getShortestPath(Station source, Station target) {
        return this.dijkstraShortestPath.getPath(source, target).getVertexList();
    }

    public List<Section> getShortestPathSections(Station source, Station target) {
        List<Station> shortestPathStations = getShortestPath(source, target);
        return IntStream.range(0, shortestPathStations.size() - 1)
                .mapToObj(it -> new Section(shortestPathStations.get(it), shortestPathStations.get(it + 1)))
                .collect(Collectors.toList());
    }

    public int getTotalDistance(Station source, Station target) {
        return (int) dijkstraShortestPath.getPathWeight(source, target);
    }
}
