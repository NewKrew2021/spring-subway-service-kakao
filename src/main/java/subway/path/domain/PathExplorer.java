package subway.path.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.line.domain.Sections;
import subway.station.domain.Station;

import java.util.List;

public class PathExplorer {

    public static Path getShortestPath(List<Line> lines, Station source, Station target) {
        WeightedMultigraph<Station, DefaultWeightedEdge> graph
                = new WeightedMultigraph(DefaultWeightedEdge.class);

        lines.stream()
                .flatMap(line -> line.getStations().stream())
                .forEach(station -> graph.addVertex(station));

        lines.forEach(line -> {
            Sections sections = line.getSections();
            sections.getSections().stream()
                    .forEach(section -> graph.setEdgeWeight(graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance()));
        });

        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(graph);
        List<Station> shortestPathOnlyVertex
                = dijkstraShortestPath.getPath(source, target).getVertexList();
        int shortestDistance = (int) dijkstraShortestPath.getPathWeight(source, target);

        return new Path(shortestPathOnlyVertex, shortestDistance);
    }
}
