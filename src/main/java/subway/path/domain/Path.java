package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.member.domain.LoginMember;
import subway.station.domain.Station;

import java.util.List;
import java.util.stream.Collectors;

public class Path {

    private final GraphPath<Station, DistanceLineEdge> graphPath;

    private Path(GraphPath<Station, DistanceLineEdge> graphPath) {
        this.graphPath = graphPath;
    }

    public static Path from(List<Line> lines, Station source, Station target) {
        WeightedMultigraph<Station, DistanceLineEdge> graph = new WeightedMultigraph<>(DistanceLineEdge.class);
        for (Line line : lines) {
            for (Section section : line.getSections()) {
                graph.addVertex(section.getUpStation());
                graph.addVertex(section.getDownStation());
                graph.addEdge(section.getUpStation(), section.getDownStation(), new DistanceLineEdge(section.getDistance(), line));
            }
        }
        ShortestPathAlgorithm<Station, DistanceLineEdge> shortestPath = new DijkstraShortestPath<>(graph);
        return new Path(shortestPath.getPath(source, target));
    }

    public List<Station> getStations() {
        return graphPath.getVertexList();
    }

    public int getDistance() {
        return (int) graphPath.getWeight();
    }

    public int getFare(LoginMember loginMember) {
        List<Line> lines = graphPath.getEdgeList()
                .stream()
                .map(DistanceLineEdge::getLine)
                .collect(Collectors.toList());
        return FareCalculator.getFare(getDistance(), lines, loginMember);
    }
}
