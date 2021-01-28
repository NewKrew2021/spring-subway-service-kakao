package subway.line.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.path.domain.Vertex;

import java.util.List;

public class Lines {
    private final List<Line> lines;
    private final DijkstraShortestPath dijkstraShortestPath;

    private Lines(List<Line> lines) {
        this.lines = lines;
        dijkstraShortestPath = setDijkstraShortestPath();
    }

    public static Lines of(List<Line> lines) {
        return new Lines(lines);
    }

    private DijkstraShortestPath setDijkstraShortestPath() {
        WeightedMultigraph<Vertex, DefaultWeightedEdge> graph
                = new WeightedMultigraph(DefaultWeightedEdge.class);
        for (Line line : lines) {
            line.fillPath(graph);
        }
        return new DijkstraShortestPath(graph);
    }

    public List<Vertex> getVertices(Long sourceId, Long targetId) {
        return dijkstraShortestPath.getPath(Vertex.of(sourceId), Vertex.of(targetId)).getVertexList();
    }

    public int getDistance(Long sourceId, Long targetId) {
        return (int) dijkstraShortestPath.getPathWeight(Vertex.of(sourceId), Vertex.of(targetId));
    }
}
