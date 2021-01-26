package subway.path.domain.path.graph;

public interface GraphElement<V, E> {

    V getSource();

    V getTarget();

    E getEdge();
}
