package subway.path.application;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.line.domain.Line;
import subway.member.domain.AGE;
import subway.path.domain.Vertex;
import subway.path.domain.strategy.FareStrategy;
import subway.path.domain.strategy.ageFare;
import subway.path.domain.strategy.distanceFare;
import subway.path.domain.strategy.lineFare;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathService {
    private final StationService stationService;
    private final LineService lineService;

    public PathService(StationService stationService, LineService lineService) {
        this.stationService = stationService;
        this.lineService = lineService;
    }

    public PathResponse getShortPath(Long sourceId, Long targetId, AGE age) {
        List<Line> lines = lineService.findLines();
        DijkstraShortestPath dijkstraShortestPath = getDijkstraShortestPath(lines);
        List<Vertex> vertexs = dijkstraShortestPath.getPath(Vertex.of(sourceId), Vertex.of(targetId)).getVertexList();

        int distance = (int) dijkstraShortestPath.getPathWeight(Vertex.of(sourceId), Vertex.of(targetId));
        int fare = 0;
        FareStrategy fareStrategy = new distanceFare(distance);
        fare = fareStrategy.apply(fare);

        fareStrategy = new lineFare(vertexs);
        fare = fareStrategy.apply(fare);

        fareStrategy = new ageFare(age);
        fare = fareStrategy.apply(fare);

        return PathResponse.of(getStationResponses(vertexs), distance, fare);
    }

    private List<StationResponse> getStationResponses(List<Vertex> vertexs) {
        return vertexs.stream()
                .map(vertex -> StationResponse.of(stationService.findStationById(vertex.getStationId())))
                .collect(Collectors.toList());
    }

    private DijkstraShortestPath getDijkstraShortestPath(List<Line> lines) {
        WeightedMultigraph<Vertex, DefaultWeightedEdge> graph
                = new WeightedMultigraph(DefaultWeightedEdge.class);
        for (Line line : lines) {
            line.fillPath(graph);
        }
        return new DijkstraShortestPath(graph);
    }
}
