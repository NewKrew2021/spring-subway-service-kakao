package subway.path.states;

import subway.path.domain.SubwayGraph;

public class UpToDate implements GraphState {
    private static UpToDate upToDate;

    private UpToDate() {
    }

    public static UpToDate getInstance() {
        if (upToDate == null) {
            upToDate = new UpToDate();
        }

        return upToDate;
    }

    @Override
    public void update(SubwayGraph subwayGraph) {
    }
}
