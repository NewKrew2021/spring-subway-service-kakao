package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.*;
import subway.station.dto.StationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SubwayPathGraph {

    private Long source;
    private Long target;
    private GraphPath<String, PathEdge> graphPath;

    public SubwayPathGraph(Lines lines, Long source, Long target) {
        this.source = source;
        this.target = target;
        init(lines);
    }

    private void init(Lines lines) {
        WeightedMultigraph<String, PathEdge> graph = new WeightedMultigraph(PathEdge.class);
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

        this.graphPath = new DijkstraShortestPath(graph)
                .getPath(source.toString(), target.toString());
    }

    public List<Long> getShortestPathStationIds() {
        return graphPath.getVertexList().stream()
                .map((stationId -> Long.parseLong(stationId)))
                .collect(Collectors.toList());
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
