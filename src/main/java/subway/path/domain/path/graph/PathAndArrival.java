package subway.path.domain.path.graph;

import org.jgrapht.GraphPath;
import subway.path.domain.path.DistanceLineEdge;
import subway.station.domain.Station;

import java.time.LocalDateTime;

public interface PathAndArrival {

    GraphPath<Station, DistanceLineEdge> getPath();

    LocalDateTime getArrivalTime();
}
