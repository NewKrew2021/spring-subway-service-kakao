package subway.path.domain;

import org.jgrapht.graph.WeightedMultigraph;

public class SubwayGraph<V, E> extends WeightedMultigraph {

    public SubwayGraph(Class edgeClass) {
        super(edgeClass);
    }

    public void setEdgeWeight(E e, SubwayWeight weight) {
        assert (e instanceof SubwayEdge) : e.getClass();
        ((SubwayEdge) e).setWeight(weight);
    }

    @Override
    public E addEdge(Object sourceVertex, Object targetVertex) {
        return (E) super.addEdge(sourceVertex, targetVertex);
    }
}
