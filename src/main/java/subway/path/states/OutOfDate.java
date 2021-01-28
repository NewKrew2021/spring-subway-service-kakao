package subway.path.states;

import subway.path.domain.SubwayGraph;

public class OutOfDate implements GraphState {
    private static OutOfDate outOfDate = null;

    private OutOfDate() {
    }

    public static OutOfDate getInstance() {
        if (outOfDate == null) {
            outOfDate = new OutOfDate();
        }

        return outOfDate;
    }

    @Override
    public void update(SubwayGraph subwayGraph) {
        subwayGraph.updateGraph();
        subwayGraph.setState(UpToDate.getInstance());
    }
}
