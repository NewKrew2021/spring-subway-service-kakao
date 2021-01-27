package subway.path.domain;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Edge extends DefaultWeightedEdge {
    private final int extraFare;
    private final int distance;
    public static final int DEFAULT_EXTRA_FARE=0;

    public Edge(int distance, int extraFare) {
        this.distance = distance;
        this.extraFare = extraFare;
    }

    @Override
    protected double getWeight() {
        return distance;
    }

    public int getExtraFare() {
        return extraFare;
    }

}
