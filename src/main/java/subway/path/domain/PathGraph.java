package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.SectionsInAllLine;
import subway.path.exceptions.UnconnectedPathException;
import subway.station.domain.Station;

public class PathGraph {

  WeightedMultigraph<Station, DefaultWeightedEdge> pathGraph;

  public PathGraph(SectionsInAllLine sections) {
    WeightedMultigraph<Station, DefaultWeightedEdge> graph = new WeightedMultigraph(
        DefaultWeightedEdge.class);

    sections.getSections()
        .forEach(section -> {
              graph.addVertex(section.getUpStation());
              graph.addVertex(section.getDownStation());

              graph.setEdgeWeight(graph.addEdge(
                  section.getUpStation(),
                  section.getDownStation()),
                  section.getDistance());
            }
        );

    this.pathGraph = graph;
  }

  public Path getPath(Station sourceStation, Station targetStation) {
    DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(pathGraph);
    GraphPath<Station, DefaultWeightedEdge> shortestPath = dijkstraShortestPath
        .getPath(sourceStation, targetStation);

    if (shortestPath == null) {
      throw new UnconnectedPathException(sourceStation, targetStation);
    }

    return new Path(shortestPath);
  }

}
