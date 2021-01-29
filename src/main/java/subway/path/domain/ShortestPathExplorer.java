package subway.path.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import subway.line.domain.Line;
import subway.station.domain.Station;

import java.util.List;

public class ShortestPathExplorer {

    private final DijkstraShortestPath dijkstraShortestPath;

    public ShortestPathExplorer(SubwayGraph subwayGraph) {
        dijkstraShortestPath = new DijkstraShortestPath(subwayGraph.getGraph());
    }

    public Path exploreShortestPath(List<Line> lines, Station source, Station target) {
        List<Station> shortestPath = dijkstraShortestPath.getPath(source, target).getVertexList();
        int shortestDistance = (int) dijkstraShortestPath.getPathWeight(source, target);

        return new Path(shortestPath, shortestDistance);
    }
}
