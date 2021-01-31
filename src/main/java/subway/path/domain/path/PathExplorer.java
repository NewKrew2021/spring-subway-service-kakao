package subway.path.domain.path;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.line.domain.Sections;
import subway.station.domain.Station;

import java.util.List;

public class PathExplorer {
    private WeightedMultigraph<Station, DefaultWeightedEdge> graph;

    public PathExplorer(List<Line> lines) {
        graph = new WeightedMultigraph(DefaultWeightedEdge.class);
        addStationVertex(lines);
        addSectionEdge(lines);
    }

    private void addStationVertex(List<Line> lines) {
        lines.stream()
                .flatMap(line -> line.getStations().stream())
                .distinct()
                .forEach(graph::addVertex);
    }

    private void addSectionEdge(List<Line> lines) {
        lines.forEach(line -> {
            Sections sections = line.getSections();
            sections.getSections()
                    .forEach(section -> graph.setEdgeWeight(
                            graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance())
                    );
        });
    }

    public Path getShortestPath(Station source, Station target) {
    	DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(graph);
    	List<Station> stations = dijkstraShortestPath.getPath(source, target).getVertexList();
        int distance = (int) dijkstraShortestPath.getPathWeight(source, target);
        
        return new Path(stations, distance);
    }
}
