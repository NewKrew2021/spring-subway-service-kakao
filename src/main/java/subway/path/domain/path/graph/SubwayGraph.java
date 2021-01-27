package subway.path.domain.path.graph;

import subway.station.domain.Station;

import java.time.LocalDateTime;

public interface SubwayGraph {

    PathAndArrival getPath(Station source, Station target, LocalDateTime departureTime);
}
