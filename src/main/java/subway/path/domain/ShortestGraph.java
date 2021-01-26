package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.List;


public class ShortestGraph {
    private GraphPath graphPath;

    public ShortestGraph(List<Line> lines, Station sourceStation, Station targetStation) {
        DijkstraShortestPath dijkstraShortestPath = getDijkstraPath(lines);
        this.graphPath = dijkstraShortestPath.getPath(sourceStation, targetStation);

    }

    private DijkstraShortestPath getDijkstraPath(List<Line> lines) {
        WeightedMultigraph<Station, WeightWithLine> graph
                = new WeightedMultigraph(WeightWithLine.class);
        for (Line line : lines) {
            line.getSections()
                    .getSections()
                    .forEach(section -> addGraph(graph, section, line));
        }
        return new DijkstraShortestPath(graph);
    }

    private void addGraph(WeightedMultigraph<Station, WeightWithLine> graph, Section section, Line line) {
        graph.addVertex(section.getUpStation());
        graph.addVertex(section.getDownStation());
        graph.addEdge(section.getUpStation(), section.getDownStation(), new WeightWithLine(section.getDistance(), line));
    }

    public GraphPath getGraphPath() {
        return graphPath;
    }

}
