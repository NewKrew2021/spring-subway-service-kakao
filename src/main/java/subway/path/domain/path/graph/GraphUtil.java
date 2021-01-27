package subway.path.domain.path.graph;

import org.jgrapht.Graph;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.path.domain.path.SubwayGraphEdge;
import subway.station.domain.Station;

import java.util.List;

public class GraphUtil {

    public static Graph<Station, SubwayGraphEdge> initializeGraph(Graph<Station, SubwayGraphEdge> graph, List<Line> lines) {
        for (Line line : lines) {
            initializeEach(graph, line);
        }
        return graph;
    }

    private static void initializeEach(Graph<Station, SubwayGraphEdge> graph, Line line) {
        for (Section section : line.getSections()) {
            graph.addVertex(section.getUpStation());
            graph.addVertex(section.getDownStation());
            graph.addEdge(
                    section.getUpStation(),
                    section.getDownStation(),
                    new SubwayGraphEdge(section.getDistance(), section.getDuration(), line)
            );
        }
    }
}
