package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.List;

public class Path {

    private final WeightedMultigraph<Station, PathGraphEdge> graph;

    public Path() {
        this.graph = new WeightedMultigraph<>(PathGraphEdge.class);
    }

    public void addStations(List<Station> stations) {
        stations.forEach(graph::addVertex);
    }

    public void addEdges(Line line) {
        for (Section section : line.getSections().getSections()) {
            Station upStation = section.getUpStation();
            Station downStation = section.getDownStation();
            int extraFare = line.getExtraFare();

            PathGraphEdge edge = graph.addEdge(upStation, downStation);
            graph.addEdge(upStation, downStation, new PathGraphEdge(section.getDistance(), extraFare));

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
