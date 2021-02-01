package subway.path.domain;

import org.jgrapht.graph.DefaultWeightedEdge;
import subway.station.domain.Station;

public class Edge extends DefaultWeightedEdge {
    private final Station source, target;
    private final int extraFare;
    private final int distance;

    public Edge(Station source, Station target, int extraFare, int distance) {
        this.source = source;
        this.target = target;
        this.extraFare = extraFare;
        this.distance = distance;
    }

    public Station getSource() {
        return source;
    }

    public Station getTarget() {
        return target;
    }

    public int getDistance() {
        return distance;
    }

    public int getExtraFare() {
        return extraFare;
    }
}
