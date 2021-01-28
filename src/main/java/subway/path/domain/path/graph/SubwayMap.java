package subway.path.domain.path.graph;

import subway.path.domain.path.SubwayPath;
import subway.station.domain.Station;

import java.time.LocalDateTime;

public interface SubwayMap {

    SubwayPath getPath(Station source, Station target, LocalDateTime departureTime);
}
