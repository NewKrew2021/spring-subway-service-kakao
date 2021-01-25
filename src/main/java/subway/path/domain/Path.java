package subway.path.domain;

import java.util.List;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import subway.station.domain.Station;

public class Path {

  GraphPath<Station, SubwayEdge> path;

  public Path(GraphPath<Station, SubwayEdge> shortestPath) {
    this.path = shortestPath;
  }

  public List<Station> getStations() {
    return path.getVertexList();
  }

  public Integer getDistance() {
    return (int) path.getWeight();
  }
}
