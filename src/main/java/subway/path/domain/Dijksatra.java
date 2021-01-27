package subway.path.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;

import java.util.List;

public class Dijksatra {
    private final DijkstraShortestPath dijkstraShortestPath;

    public Dijksatra(List<Line> lines) {
        this.dijkstraShortestPath = getDijkstraShortestPath(lines);
    }

    public int getPathWeight(Long sourceId, Long targetId) {
        return (int) dijkstraShortestPath.getPathWeight(Vertex.of(sourceId), Vertex.of(targetId));
    }

    public List<Vertex> getPathVertices(Long sourceId, Long targetId) {
        return dijkstraShortestPath.getPath(Vertex.of(sourceId), Vertex.of(targetId)).getVertexList();
    }

    public List<Edge> getPathEdges(Long sourceId, Long targetId) {
        return dijkstraShortestPath.getPath(Vertex.of(sourceId), Vertex.of(targetId)).getEdgeList();
    }

    private DijkstraShortestPath getDijkstraShortestPath(List<Line> lines) {
        WeightedMultigraph<Vertex, Edge> graph = new WeightedMultigraph(Edge.class);
        for (Line line : lines) {
            line.fillPath(graph);
        }
        return new DijkstraShortestPath(graph);
    }

}
