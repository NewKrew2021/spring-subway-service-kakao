package subway.path.application;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.fare.FareService;
import subway.line.application.LineService;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.member.domain.LoginMember;
import subway.path.domain.Path;
import subway.path.dto.PathResponse;
import subway.station.application.StationService;
import subway.station.domain.Station;

import java.util.List;

@Service
public class PathService {

    private StationService stationService;
    private LineService lineService;
    private FareService fareService;

    @Autowired
    public PathService(StationService stationService, LineService lineService, FareService fareService) {
        this.stationService = stationService;
        this.lineService = lineService;
        this.fareService = fareService;
    }

    public PathResponse getPath(LoginMember loginMember, Long sourceStationId, Long targetStationId) {

        WeightedMultigraph<Station, Section> graph = makeGraph();

        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(graph);


        GraphPath graphPath = dijkstraShortestPath.getPath(
                stationService.findStationById(sourceStationId),
                stationService.findStationById(targetStationId)
        );

        int distance = (int) graphPath.getWeight();
        Sections sections = new Sections(graphPath.getEdgeList());

        int fare = fareService.findFare(sections.getLineIds(), distance, loginMember);

        Path path = new Path(graphPath.getVertexList(), distance, fare);

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
}
