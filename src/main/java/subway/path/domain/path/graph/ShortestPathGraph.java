package subway.path.domain.path.graph;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;

public class ShortestPathGraph<V, E> implements Graph<V, E> {

    private final WeightedMultigraph<V, E> graph;

    private ShortestPathGraph(WeightedMultigraph<V, E> graph) {
        this.graph = graph;
    }

    public static <V, E> Graph<V, E> initialize(Class<E> edge) {
        return new ShortestPathGraph<>(new WeightedMultigraph<>(edge));
    }

    @Override
    public void add(V source, V target, E edge) {
        graph.addVertex(source);
        graph.addVertex(target);
        graph.addEdge(source, target, edge);
    }

    @Override
    public Path<V, E> getPath(V source, V target) {
        return new PathImpl<>(new DijkstraShortestPath<>(graph).getPath(source, target));
    }
}
