package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import subway.line.domain.SectionsInAllLine;
import subway.path.exceptions.UnconnectedPathException;
import subway.path.libs.SubwayEdge;
import subway.path.libs.SubwayGraph;
import subway.path.libs.SubwayWeight;
import subway.station.domain.Station;

public class PathGraph {

    SubwayGraph<Station, SubwayEdge> pathGraph;

    public PathGraph(SectionsInAllLine sections) {
        SubwayGraph<Station, SubwayEdge> graph = new SubwayGraph<>(SubwayEdge.class);

        sections.getSections()
                .forEach(section -> {
                            graph.addVertex(section.getUpStation());
                            graph.addVertex(section.getDownStation());

                            graph.setEdgeWeight(graph.addEdge(
                                    section.getUpStation(),
                                    section.getDownStation()),
                                    new SubwayWeight(section.getExtraFare(), section.getDistance()));
                        }
                );

        this.pathGraph = graph;
    }

    public Path getPath(Station sourceStation, Station targetStation) {
        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(pathGraph);
        GraphPath<Station, SubwayEdge> shortestPath = dijkstraShortestPath
                .getPath(sourceStation, targetStation);

        if (shortestPath == null) {
            throw new UnconnectedPathException(sourceStation, targetStation);
        }

        return new Path(shortestPath);
    }

}
