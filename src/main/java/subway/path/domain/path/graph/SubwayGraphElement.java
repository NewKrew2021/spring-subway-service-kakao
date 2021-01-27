package subway.path.domain.path.graph;

import subway.path.domain.path.DistanceLineEdge;
import subway.station.domain.Station;

public class SubwayGraphElement {

    private final Station source;
    private final Station target;
    private final DistanceLineEdge edge;

    public SubwayGraphElement(Station source, Station target, DistanceLineEdge edge) {
        this.source = source;
        this.target = target;
        this.edge = edge;
    }

    public Station getSource() {
        return source;
    }

    public Station getTarget() {
        return target;
    }

    public DistanceLineEdge getEdge() {
        return edge;
    }
}
