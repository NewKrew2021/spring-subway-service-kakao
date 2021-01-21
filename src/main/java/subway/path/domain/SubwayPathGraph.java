package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Section;
import subway.line.domain.Sections;

import java.util.ArrayList;
import java.util.List;

public class SubwayPathGraph {

    private final WeightedMultigraph<String, DefaultWeightedEdge> graph;

    private Long source;
    private Long target;
    private DijkstraShortestPath dijkstraShortestPath;
    private GraphPath graphPath;


    public SubwayPathGraph(Sections sections, Long source, Long target) {
        this.graph = new WeightedMultigraph(DefaultWeightedEdge.class);
        this.dijkstraShortestPath = new DijkstraShortestPath(graph);
        this.source = source;
        this.target = target;
        init(sections);
    }

    public void init(Sections sections) {
        List<Long> stationIds = new ArrayList<>(sections.getStationIdsSet());

        for (Long stationId : stationIds) {
            graph.addVertex(stationId.toString());
        }

        for (Section section : sections.getSections()) {
            graph.setEdgeWeight(graph.addEdge(
                    section.getUpStation().getId().toString(),
                    section.getDownStation().getId().toString()),
                    section.getDistance());
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
}
