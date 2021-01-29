package subway.line.domain;

import org.jgrapht.graph.DefaultWeightedEdge;

public class PathEdge extends DefaultWeightedEdge {

    private Long lineId;
    private int distance;

    public PathEdge(Long lineId, int distance) {
        this.lineId = lineId;
        this.distance = distance;
    }

    @Override
    protected double getWeight() {
        return distance;
    }

    public Long getLineId() {
        return lineId;
    }
}
