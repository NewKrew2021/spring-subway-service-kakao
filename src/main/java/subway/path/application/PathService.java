package subway.path.application;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.path.domain.Path;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;
import subway.station.domain.Station;

import java.util.List;

@Service
public class PathService {

    private StationService stationService;
    private LineService lineService;

    @Autowired
    public PathService(StationService stationService, LineService lineService) {
        this.stationService = stationService;
        this.lineService = lineService;
    }

    public PathResponse getPath(Long sourceStationId, Long targetStationId) {

        List<Line> lines = lineService.findLines();
        Sections sections = new Sections();

        for (Line line : lines) {
            sections.addSections(line.getSections());
        }

        List<Station> stations = stationService.findAll();


        WeightedMultigraph<Station, DefaultWeightedEdge> graph = new WeightedMultigraph(DefaultWeightedEdge.class);

        stations.forEach(graph::addVertex);

        for (Section section : sections.getSections()) {
            graph.setEdgeWeight(graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance());
        }

        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(graph);


        GraphPath graphPath = dijkstraShortestPath.getPath(
                stationService.findStationById(sourceStationId),
                stationService.findStationById(targetStationId)
        );

        Path path = new Path(graphPath.getVertexList(), (int) graphPath.getWeight());

        return PathResponse.of(path);
    }
}
