package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Sections;
import subway.path.dto.PathResult;
import subway.station.domain.Station;

import java.util.List;

public class Path {

    WeightedMultigraph<Station, DefaultWeightedEdge> graph
            = new WeightedMultigraph(DefaultWeightedEdge.class);

    public Path(List<Station> stations, Sections sections){
        stations.forEach(station -> graph.addVertex(station));
        sections.getSections()
                .forEach(section -> graph.setEdgeWeight(graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance()));
    }

    public PathResult findShortestPath(Station sourceStation, Station targetStation) {
        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(this.graph);
        GraphPath result = dijkstraShortestPath.getPath(sourceStation, targetStation);
        List<Station> vertexList = result.getVertexList();
        int distance = (int) result.getWeight();
        return new PathResult(vertexList, distance);
    }
}
