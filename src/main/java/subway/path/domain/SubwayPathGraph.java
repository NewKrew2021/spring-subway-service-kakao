package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SubwayPathGraph {

    private final WeightedMultigraph<String, PathEdge> graph;

    private Long source;
    private Long target;
    private DijkstraShortestPath dijkstraShortestPath;
    private GraphPath<String, PathEdge> graphPath;


    public SubwayPathGraph(Lines lines, Long source, Long target) {
        this.graph = new WeightedMultigraph(PathEdge.class);
        this.dijkstraShortestPath = new DijkstraShortestPath(graph);
        this.source = source;
        this.target = target;
        init(lines);
    }

    public void init(Lines lines) {
        List<Long> stationIds = new ArrayList<>(lines.getAllSections().getStationIdsSet());

        for (Long stationId : stationIds) {
            graph.addVertex(stationId.toString());
        }

        for (Line line : lines.getLines()) {
            for (Section section : line.getSections().getSections()) {
                graph.addEdge(section.getUpStation().getId().toString(),
                        section.getDownStation().getId().toString(),
                        new PathEdge(line.getId(), section.getDistance()));
            }
        }

        this.graphPath = dijkstraShortestPath
                .getPath(source.toString(), target.toString());
    }

    public List<String> getVertexList() {
        return graphPath.getVertexList();
    }

    public int getTotalDistance() {
        return (int) graphPath.getWeight();
    }

    public List<Long> getLineIdsInShortestPath(){
        return graphPath.getEdgeList().stream()
                .map(edge -> edge.getLineId())
                .distinct()
                .collect(Collectors.toList());
    }
}
