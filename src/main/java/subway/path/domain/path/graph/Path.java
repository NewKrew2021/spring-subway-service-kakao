package subway.path.domain.path.graph;

import java.util.List;

public interface Path<V, E> {

    List<V> getVertexes();

    List<E> getEdges();

    double getTotalWeight();
}
