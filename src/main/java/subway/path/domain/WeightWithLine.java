package subway.path.domain;

import org.jgrapht.graph.DefaultWeightedEdge;
import subway.line.domain.Line;

public class WeightWithLine extends DefaultWeightedEdge {
    private int distance;
    private Line line;

    public WeightWithLine(int distance, Line line) {
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
