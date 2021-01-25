package subway.path.domain;

import org.jgrapht.graph.DefaultWeightedEdge;

public class SubwayEdge extends DefaultWeightedEdge {
    SubwayWeight weight;

    @Override
    protected double getWeight() {
        return weight.getDistance();
    }

    public int getFare() {
        return weight.getFare();
    }
}
