package subway.path.states;

import subway.path.domain.SubwayGraph;

public interface GraphState {
    void update(SubwayGraph subwayGraph);
}
