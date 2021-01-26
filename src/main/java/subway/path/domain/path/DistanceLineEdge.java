package subway.path.domain.path;

import org.jgrapht.graph.DefaultWeightedEdge;
import subway.line.domain.Line;

public class DistanceLineEdge extends DefaultWeightedEdge {
    private final int distance;
    private final Line line;

    public DistanceLineEdge(int distance, Line line) {
        this.distance = distance;
        this.line = line;
    }

    @Override
    protected double getWeight() {
        return distance;
    }

    public Line getLine() {
        return line;
    }
}
