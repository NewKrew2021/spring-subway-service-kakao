package subway.path.domain.path.graph;

import subway.station.domain.Station;

import java.time.LocalDateTime;

public interface SubwayGraphPath  {

    PathAndArrival getPath(Station source, Station target, LocalDateTime departureTime);
}
