package subway.path.domain.path;

import org.jgrapht.graph.DefaultWeightedEdge;
import subway.line.domain.Line;

public class SubwayGraphEdge extends DefaultWeightedEdge {
    private final int distance;
    private final int duration;
    private final Line line;

    public SubwayGraphEdge(int distance, int duration, Line line) {
        this.distance = distance;
        this.duration = duration;
        this.line = line;
    }

    @Override
    protected double getWeight() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public Line getLine() {
        return line;
    }
}
