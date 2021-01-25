package subway.path.domain.path.graph;

public interface Graph<V, E> {

    void add(V source, V target, E edge);

    Path<V, E> getPath(V source, V target);
}
