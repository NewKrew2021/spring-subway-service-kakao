package subway.path.domain.path;

import org.jgrapht.graph.DefaultWeightedEdge;
import subway.common.domain.Distance;
import subway.common.domain.Fare;

public class CustomWeightedEdge extends DefaultWeightedEdge {
    private final Distance distance;
    private final Fare extraFare;

    private CustomWeightedEdge(Distance distance, Fare extraFare) {
        this.distance = distance;
        this.extraFare = extraFare;
    }

    public static CustomWeightedEdge of(Distance distance, Fare extraFare) {
        return new CustomWeightedEdge(distance, extraFare);
    }

    @Override
    public double getWeight(){
        return distance.getDistance();
    }

    public Distance getDistance() {
        return distance;
    }

    public Fare getExtraFare() {
        return extraFare;
    }
}
