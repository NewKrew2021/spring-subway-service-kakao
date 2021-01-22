package subway.path.application;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.line.domain.Line;
import subway.member.domain.AGE;
import subway.path.domain.DistanceFare;
import subway.path.domain.Vertex;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathService {
    public static final int DEFAULT_EXTRA_FARE = 0;

    private final StationService stationService;
    private final LineService lineService;

    @Autowired
    public PathService(StationService stationService, LineService lineService) {
        this.stationService = stationService;
        this.lineService = lineService;
    }

    public PathResponse getShortPath(Long sourceId, Long targetId, AGE age) {
        List<Line> lines = lineService.findLines();
        DijkstraShortestPath dijkstraShortestPath = getDijkstraShortestPath(lines);
        List<Vertex> vertexs = dijkstraShortestPath.getPath(Vertex.of(sourceId, DEFAULT_EXTRA_FARE), Vertex.of(targetId, DEFAULT_EXTRA_FARE)).getVertexList();

        int distance = (int) dijkstraShortestPath.getPathWeight(Vertex.of(sourceId, DEFAULT_EXTRA_FARE), Vertex.of(targetId, DEFAULT_EXTRA_FARE));
        int distanceFare = DistanceFare.getDistanceFare(distance);
        int lineExtraFare = vertexs.stream()
                .map(v -> v.getExtraFare())
                .max(Integer::compare).orElse(DEFAULT_EXTRA_FARE);

        return PathResponse.of(vertexs.stream()
                .map(vertex -> StationResponse.of(stationService.findStationById(vertex.getStationId())))
                .collect(Collectors.toList()), distance, calculateFare(age, distanceFare, lineExtraFare));
    }

    private int calculateFare(AGE age, int distanceFare, int lineExtraFare) {
        return (int) ((distanceFare + lineExtraFare) * age.getSale() + age.getDeduction());
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
