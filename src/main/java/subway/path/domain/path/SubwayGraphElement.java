package subway.path.domain.path;

import subway.path.domain.path.graph.GraphElement;
import subway.station.domain.Station;

public class SubwayGraphElement implements GraphElement<Station, DistanceLineEdge> {

    private final Station source;
    private final Station target;
    private final DistanceLineEdge edge;

    public SubwayGraphElement(Station source, Station target, DistanceLineEdge edge) {
        this.source = source;
        this.target = target;
        this.edge = edge;
    }

    @Override
    public Station getSource() {
        return source;
    }

    @Override
    public Station getTarget() {
        return target;
    }

    @Override
    public DistanceLineEdge getEdge() {
        return edge;
    }
}
