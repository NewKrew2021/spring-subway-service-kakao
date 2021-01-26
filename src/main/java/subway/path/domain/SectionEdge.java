package subway.path.domain;

import org.jgrapht.graph.DefaultWeightedEdge;
import subway.station.domain.Station;

public class SectionEdge extends DefaultWeightedEdge {
    private final Station v1;
    private final Station v2;
    private final int distance;
    private final int fare;

    public SectionEdge(Station v1, Station v2, int distance, int fare) {
        this.v1 = v1;
        this.v2 = v2;
        this.distance = distance;
        this.fare = fare;
    }

    public Station getV1() {
        return v1;
    }

    public Station getV2() {
        return v2;
    }

    public int getDistance() {
        return distance;
    }

    public int getFare() {
        return fare;
    }

    @Override
    protected double getWeight() {
        return distance + fare / 10000.0;
    }
}
