package subway.path.domain.path;

import org.jgrapht.graph.DefaultWeightedEdge;

public class CustomWeightedEdge extends DefaultWeightedEdge {
    private int distance;
    private int extraFare;

    public CustomWeightedEdge(int distance, int extraFare) {
        this.distance = distance;
        this.extraFare = extraFare;
    }

    @Override
    public double getWeight(){
        return distance;
    }

    public int getDistance() {
        return distance;
    }

    public int getExtraFare() {
        return extraFare;
    }
}
