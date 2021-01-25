package subway.path.domain;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.station.domain.Station;

import java.util.Collection;
import java.util.List;

public class Path {
    private List<Station> stations;
    private int distance;
    private int fare;

    public Path(List<Station> stations, int distance, int fare) {
        this.stations = stations;
        this.distance = distance;
        this.fare = fare;
    }

    public static Path make(List<Line> lines, Station sourceStation, Station targetStation) {
        DijkstraShortestPath dijkstraShortestPath = getDijkstraPath(lines);

        return new Path(
                dijkstraShortestPath.getPath(sourceStation, targetStation).getVertexList(),
                (int) dijkstraShortestPath.getPath(sourceStation, targetStation).getWeight(),
                0
        );

    }

    private static DijkstraShortestPath getDijkstraPath(List<Line> lines) {
        WeightedMultigraph<Station, WeightWithLine> graph
                = new WeightedMultigraph(WeightWithLine.class);
        for (Line line : lines) {
            line.getSections()
                    .getSections()
                    .forEach(section -> addGraph(graph, section, line));
        }

        return new DijkstraShortestPath(graph);
    }

    private static void addGraph(WeightedMultigraph<Station, WeightWithLine> graph, Section section, Line line) {
        Station upStation = section.getUpStation();
        Station downStation = section.getDownStation();
        WeightWithLine weightWithLine = new WeightWithLine(section.getDistance(), line);
        if (!graph.containsVertex(upStation)) {
            graph.addVertex(upStation);
        }
        if (!graph.containsVertex(downStation)) {
            graph.addVertex(downStation);
        }
        graph.addEdge(upStation, downStation, weightWithLine);
        graph.addEdge(downStation, upStation, weightWithLine);

    }

    public int getDistance() {
        return distance;
    }

    public int getFare() {
        return fare;
    }

    public List<Station> getStations() {
        return stations;
    }
}
