package subway.path.libs;

import org.jgrapht.graph.DefaultWeightedEdge;

public class SubwayEdge extends DefaultWeightedEdge {

    private SubwayWeight weight;

    @Override
    protected double getWeight() {
        return weight.getDistance();
    }

    public int getFare() {
        return weight.getFare();
    }

    public void setWeight(SubwayWeight weight) {
        this.weight = weight;
    }
}
