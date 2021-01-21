package subway.path.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.List;

public class SubwayGraph {
    private final DijkstraShortestPath<Station, DefaultWeightedEdge> dijkstraShortestPath;

    public SubwayGraph(List<Section> sections) {
        WeightedMultigraph<Station, DefaultWeightedEdge> graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);
        makeGraph(sections, graph);

        dijkstraShortestPath = new DijkstraShortestPath<>(graph);
    }

    public List<Station> getShortestPath(Station source, Station target) {
        return dijkstraShortestPath.getPath(source, target).getVertexList();
    }

    public Long getShortestDistance(Station source, Station target) {
        return (long) dijkstraShortestPath.getPath(source, target).getWeight();
    }

   private void makeGraph(List<Section> sections, WeightedMultigraph<Station, DefaultWeightedEdge> graph) {
        for (Section section : sections) {
            Station upStation = section.getUpStation();
            Station downStation = section.getDownStation();

            graph.addVertex(upStation);
            graph.addVertex(downStation);
            graph.setEdgeWeight(graph.addEdge(upStation, downStation), section.getDistance());
        }
    }
}
