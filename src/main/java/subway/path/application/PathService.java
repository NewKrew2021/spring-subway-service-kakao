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

import java.util.ArrayList;
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

        WeightedMultigraph<Station, Section> graph = makeGraph();

        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(graph);


        GraphPath graphPath = dijkstraShortestPath.getPath(
                stationService.findStationById(sourceStationId),
                stationService.findStationById(targetStationId)
        );

        Sections sections = new Sections(graphPath.getEdgeList());

        int fare = 1250;

        fare += findMaxLineFare(sections);

        Path path = new Path(graphPath.getVertexList(), (int) graphPath.getWeight(), fare);

        return PathResponse.of(path);

    }

    private WeightedMultigraph<Station, Section> makeGraph() {

        Sections sections = lineService.findAllSections();

        List<Station> stations = stationService.findAll();

        WeightedMultigraph<Station, Section> graph = new WeightedMultigraph(DefaultWeightedEdge.class);

        stations.forEach(graph::addVertex);

        for (Section section : sections.getSections()) {
            graph.addEdge(section.getUpStation(), section.getDownStation(), section);
        }

        return graph;
    }

    private int findMaxLineFare(Sections sections) {
        int maxExtraFare = 0;

        for (Section section : sections.getSections()) {
            Long lineId = section.getLindId();
            maxExtraFare = Integer.max(maxExtraFare, lineService.findExtraFare(lineId));
        }

        return maxExtraFare;
    }
}
