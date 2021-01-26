package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.exception.CannotFindPathException;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.member.domain.LoginMember;
import subway.station.domain.Station;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private final WeightedMultigraph<Station, Edge> graph = new WeightedMultigraph<>(Edge.class);

    public Graph(List<Line> lines) {
        lines.stream()
                .map(Line::getStations)
                .flatMap(List::stream)
                .distinct().forEach(graph::addVertex);

        for(Line line : lines){
            addAllSectionsAsEdge(line);
        }

    }

    private void addAllSectionsAsEdge(Line line) {
        for(Section section : line.getSections().getSections()) {
            Edge edge = new Edge(section.getUpStation(), section.getDownStation(), line.getExtraFare(), section.getDistance());
            graph.addEdge(edge.getSource(), edge.getTarget(), edge);
            graph.setEdgeWeight(edge, edge.getDistance());
        }
    }

    public Path getPath(Station source, Station target, Optional<LoginMember> loginMemberOptional) {
        GraphPath<Station, Edge> shortestPath = new DijkstraShortestPath<>(graph).getPath(source, target);
        pathExistenceCheck(shortestPath);

        int distance = (int)shortestPath.getWeight();
        List<Integer> extraFares = shortestPath.getEdgeList().stream().map(Edge::getExtraFare).collect(Collectors.toList());

        int fare = Fare.calculate(distance, extraFares);
        if(loginMemberOptional.isPresent()) {
            fare = Fare.discountByAge(fare, loginMemberOptional.get().getAge());
        }

        List<Station> vertices = shortestPath.getVertexList();
        return new Path(vertices, distance, fare);
    }

    private void pathExistenceCheck(GraphPath<Station, Edge> shortestPath) {
        if(null == shortestPath) {
            throw new CannotFindPathException("경로를 존재하지 않습니다.");
        }
    }
}
