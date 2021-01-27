package subway.path.domain.path.graph;

import subway.path.domain.path.SubwayGraphEdge;
import subway.station.domain.Station;

public class SubwayGraphElement {

    private final Station source;
    private final Station target;
    private final SubwayGraphEdge edge;

    public SubwayGraphElement(Station source, Station target, SubwayGraphEdge edge) {
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

    public SubwayGraphEdge getEdge() {
        return edge;
    }
}
