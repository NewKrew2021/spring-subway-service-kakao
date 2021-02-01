package subway.path.domain.path;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.common.domain.Distance;
import subway.common.domain.Fare;
import subway.common.exception.NotExistException;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import java.util.List;

public class Path {
    private final List<Station> stations;
    private final Distance distance;
    private final Fare extraFare;

    public Path(Station sourceStation, Station destStation, List<Line> lines) {
        GraphPath<Station, CustomWeightedEdge> path = getPath(sourceStation, destStation, lines);
        validate(path);
        this.stations = path.getVertexList();
        this.distance = Distance.from((int) path.getWeight());
        this.extraFare = path.getEdgeList()
                .stream()
                .map(CustomWeightedEdge::getExtraFare)
                .reduce(Fare::max)
                .orElseThrow(() -> new AssertionError("유효한 경로는 반드시 요금을 포함해야 합니다."));
    }

    private GraphPath<Station, CustomWeightedEdge> getPath(Station sourceStation, Station destStation, List<Line> lines) {
        WeightedMultigraph<Station, CustomWeightedEdge> graph = new WeightedMultigraph<>(CustomWeightedEdge.class);
        for (Line line : lines) {
            addVerticesAndEdge(graph, line);
        }
        return new DijkstraShortestPath<>(graph).getPath(sourceStation, destStation);
    }

    private void validate(GraphPath<Station, CustomWeightedEdge> path) {
        if (path == null) {
            throw new NotExistException("경로가 존재하지 않습니다.");
        }
    }

    private void addVerticesAndEdge(WeightedMultigraph<Station, CustomWeightedEdge> graph, Line line) {
        for (Section section : line.getSections().getSections()) {
            Station upStation = section.getUpStation();
            Station downStation = section.getDownStation();
            graph.addVertex(upStation);
            graph.addVertex(downStation);
            graph.addEdge(upStation, downStation, CustomWeightedEdge.of(section.getDistance(), line.getExtraFare()));
        }
    }

    public List<Station> getStations() {
        return stations;
    }

    public Distance getDistance() {
        return distance;
    }

    public Fare getExtraFare() {
        return extraFare;
    }
}
