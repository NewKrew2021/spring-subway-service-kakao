package subway.path.domain.path.graph;

import org.jgrapht.GraphPath;

import java.util.List;

public class PathImpl<V, E> implements Path<V, E> {

    private final GraphPath<V, E> graphPath;

    public PathImpl(GraphPath<V, E> graphPath) {
        this.graphPath = graphPath;
    }

    @Override
    public List<V> getVertexes() {
        return graphPath.getVertexList();
    }

    @Override
    public List<E> getEdges() {
        return graphPath.getEdgeList();
    }

    @Override
    public double getTotalWeight() {
        return graphPath.getWeight();
    }
}
