package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.station.domain.Station;

import java.util.List;

public class Path {

    private final WeightedMultigraph<Station, DefaultWeightedEdge> graph;

    public Path() {
        this.graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);
    }

    public void addStations(List<Station> stations) {
        stations.forEach(graph::addVertex);
    }

    public void addEdges(Sections sections) {
        for (Section section : sections.getSections()) {
            Station upStation = section.getUpStation();
            Station downStation = section.getDownStation();

            DefaultWeightedEdge edge = graph.addEdge(upStation, downStation);

            graph.setEdgeWeight(edge, section.getDistance());
        }
    }

    public GraphPath findShortestPathGraph(Station source, Station target) {
        DijkstraShortestPath dijkstraShortestPath
                = new DijkstraShortestPath<>(graph);
        return dijkstraShortestPath.getPath(source, target);
    }

    public boolean containStation(Station station) {
        return graph.containsVertex(station);
    }

    public boolean containEdge(Station source, Station target) {
        return graph.containsEdge(source, target);
    }

}
