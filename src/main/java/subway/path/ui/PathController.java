package subway.path.ui;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import subway.line.application.LineService;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

import java.util.List;

@RestController
@RequestMapping("/paths")
public class PathController {
    private final LineService lineService;
    private final StationService stationService;

    public PathController(LineService lineService, StationService stationService) {
        this.lineService = lineService;
        this.stationService = stationService;
    }

    @GetMapping
    public ResponseEntity<PathResponse> findPaths(@RequestParam long source, @RequestParam long target) {
        WeightedMultigraph<Station, DefaultWeightedEdge> graph
                = new WeightedMultigraph<>(DefaultWeightedEdge.class);

        List<Line> lines = lineService.findLines();
        for (Line line : lines) {
            for (Section section : line.getSections().getSections()) {
                graph.addVertex(section.getUpStation());
                graph.addVertex(section.getDownStation());
                graph.setEdgeWeight(graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance());
            }
        }

        DijkstraShortestPath<Station, DefaultWeightedEdge> dijkstraShortestPath
                = new DijkstraShortestPath<>(graph);
        GraphPath<Station, DefaultWeightedEdge> path = dijkstraShortestPath.getPath(stationService.findStationById(source), stationService.findStationById(target));

        PathResponse pathResponse = new PathResponse(StationResponse.listOf(path.getVertexList()), (int) path.getWeight(), 0);

        return ResponseEntity.ok(pathResponse);
    }
}
