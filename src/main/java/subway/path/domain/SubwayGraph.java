package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.station.domain.Station;

import java.util.List;

public class SubwayGraph {
    private final DijkstraShortestPath<Station, SectionEdge> dijkstraShortestPath;

    public SubwayGraph(List<SectionEdge> sectionEdges) {
        WeightedMultigraph<Station, SectionEdge> graph = new WeightedMultigraph<>(SectionEdge.class);
        makeGraph(sectionEdges, graph);

        dijkstraShortestPath = new DijkstraShortestPath<>(graph);
    }

    public Path getShortestPath(Station source, Station target) {
        GraphPath<Station, SectionEdge> path = dijkstraShortestPath.getPath(source, target);
        double pathWeight = dijkstraShortestPath.getPathWeight(source, target);
        return new Path(path.getVertexList(), path.getEdgeList(), (int) pathWeight);
    }

    private void makeGraph(List<SectionEdge> sectionEdges, WeightedMultigraph<Station, SectionEdge> graph) {
        for (SectionEdge sectionEdge : sectionEdges) {
            Station upStation = sectionEdge.getV1();
            Station downStation = sectionEdge.getV2();

            graph.addVertex(upStation);
            graph.addVertex(downStation);
            graph.addEdge(upStation, downStation, sectionEdge);
        }
    }
}