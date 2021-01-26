package subway.path.domain;

import org.jgrapht.graph.DefaultWeightedEdge;

public class PathGraphEdge extends DefaultWeightedEdge {

    private int distance;
    private int extraFare;

    public PathGraphEdge() {
    }

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
