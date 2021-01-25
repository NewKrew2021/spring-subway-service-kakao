package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.*;

public class Graph {
    private final WeightedMultigraph<Station, Edge> graph = new WeightedMultigraph<>(Edge.class);
    private final DijkstraShortestPath<Station, Edge> dijkstraShortestPath;

    public Graph(List<Line> lines) {
        lines.stream()
                .map(Line::getStations)
                .flatMap(List::stream)
                .distinct().forEach(graph::addVertex);

        for(Line line : lines){
            addAllSectionsAsEdge(line);
        }

        dijkstraShortestPath = new DijkstraShortestPath<>(graph);
    }

    private void addAllSectionsAsEdge(Line line) {
        for(Section section : line.getSections().getSections()) {
            Edge edge = new Edge(section.getUpStation(), section.getDownStation(), line.getExtraFare(), section.getDistance());
            graph.addEdge(edge.getSource(), edge.getTarget(), edge);
            graph.setEdgeWeight(edge, edge.getDistance());
        }
    }

    public Path getPath(Station source, Station target, int age) {
        GraphPath<Station, Edge> shortestPath = dijkstraShortestPath.getPath(source, target);
        if (shortestPath == null) {
            throw new RuntimeException("경로가 없습니다.");
        }

        List<Station> vertices = shortestPath.getVertexList();
        int distance = (int)shortestPath.getWeight();
        int fare = calculateFare(distance, shortestPath.getEdgeList());
        // age 관련계산
        if(age >=6 && age < 13){
            fare = fare - (int)((fare - 350) * 0.5);
        }
        if(age >= 13 && age < 19){
            fare = fare - (int)((fare - 350) * 0.2);
        }

        return new Path(vertices, distance, fare);
    }

    private int calculateFare(int distance, List<Edge> edgeList) {
        int distanceFare = 1250 +
                (int)Math.ceil(Math.min( Math.max((distance - 10), 0), 40 ) / 5.0) * 100 +
                (int)Math.ceil(Math.max((distance - 50), 0) / 8.0) * 100;

        int maxFare = 0;
        for (Edge edge : edgeList) {
            maxFare = Math.max(maxFare, edge.getExtraFare());
        }
        return distanceFare + maxFare;
    }
}
