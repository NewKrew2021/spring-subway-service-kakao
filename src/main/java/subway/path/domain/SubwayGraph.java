package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.path.states.GraphState;
import subway.path.states.OutOfDate;
import subway.station.domain.Station;

import java.util.List;
import java.util.function.Supplier;

public class SubwayGraph {
    private final Supplier<List<SectionEdge>> graphUpdater;
    private DijkstraShortestPath<Station, SectionEdge> dijkstraShortestPath;
    private GraphState state;

    public SubwayGraph(Supplier<List<SectionEdge>> updater) {
        graphUpdater = updater;
        this.state = OutOfDate.getInstance();
    }

    public void setState(GraphState state) {
        this.state = state;
    }

    public Path getShortestPath(Station source, Station target) {
        state.update(this);

        GraphPath<Station, SectionEdge> path = dijkstraShortestPath.getPath(source, target);
        double pathWeight = dijkstraShortestPath.getPathWeight(source, target);
        return new Path(path.getVertexList(), path.getEdgeList(), (int) pathWeight);
    }

    public void updateGraph() {
        List<SectionEdge> sectionEdges = graphUpdater.get();
        WeightedMultigraph<Station, SectionEdge> graph = new WeightedMultigraph<>(SectionEdge.class);
        makeGraph(sectionEdges, graph);

        dijkstraShortestPath = new DijkstraShortestPath<>(graph);
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