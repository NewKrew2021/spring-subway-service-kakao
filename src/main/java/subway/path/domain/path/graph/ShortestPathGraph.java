package subway.path.domain.path.graph;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.List;

public class ShortestPathGraph<V, E> {

    private final Graph<V, E> graph;

    private ShortestPathGraph(Graph<V, E> graph) {
        this.graph = graph;
    }

    public static <V, E> ShortestPathGraph<V, E> initialize(List<GraphElement<V, E>> graphElements, Class<E> edgeClass) {
        Graph<V, E> graph = new WeightedMultigraph<>(edgeClass);
        for (GraphElement<V, E> element : graphElements) {
            graph.addVertex(element.getSource());
            graph.addVertex(element.getTarget());
            graph.addEdge(element.getSource(), element.getTarget(), element.getEdge());
        }
        return new ShortestPathGraph<>(graph);
    }

    public GraphPath<V, E> getPath(V source, V target) {
        return new DijkstraShortestPath<>(graph).getPath(source, target);
    }
}
