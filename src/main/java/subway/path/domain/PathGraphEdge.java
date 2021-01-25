package subway.path.domain;

import org.jgrapht.graph.DefaultWeightedEdge;

public class PathGraphEdge extends DefaultWeightedEdge {

    private final int distance;
    private final int extraFare;

    public PathGraphEdge(int distance, int extraFare) {
        this.distance = distance;
        this.extraFare = extraFare;
    }

    public int getExtraFare() {
        return extraFare;
    }

    @Override
    protected double getWeight() {
        return distance;
    }
}
