package subway.path.domain;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.WeightedMultigraph;

public class SubwayGraph<V, E> extends WeightedMultigraph {

    public SubwayGraph(EdgeFactory ef) {
        super(ef);
    }

    public SubwayGraph(Class edgeClass) {
        super(edgeClass);
    }

    public void setEdgeWeight(E e, SubwayWeight weight) {
        assert (e instanceof SubwayEdge) : e.getClass();
        ((SubwayEdge) e).weight = weight;
    }

    @Override
    public E addEdge(Object sourceVertex, Object targetVertex) {
        return (E) super.addEdge(sourceVertex, targetVertex);
    }
}
