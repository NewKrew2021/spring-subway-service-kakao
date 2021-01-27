package subway.path.domain;

import org.jgrapht.graph.DefaultWeightedEdge;

public class SubwayEdge extends DefaultWeightedEdge {
    private final int extraFare;
    private final int distance;

    public SubwayEdge(int distance, int extraFare) {
        this.distance = distance;
        this.extraFare = extraFare;
    }

    @Override
    protected double getWeight() {
        return distance;
    }

    public int getFare() {
        return extraFare;
    }
}
