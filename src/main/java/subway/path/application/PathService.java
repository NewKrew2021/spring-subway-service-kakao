package subway.path.application;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Service;
import subway.fare.application.FareService;
import subway.line.application.LineService;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.member.domain.LoginMember;
import subway.path.domain.Path;
import subway.station.application.StationService;
import subway.station.domain.Station;

import java.util.List;

@Service
public class PathService {

    private StationService stationService;
    private LineService lineService;
    private FareService fareService;

    public PathService(StationService stationService, LineService lineService, FareService fareService) {
        this.stationService = stationService;
        this.lineService = lineService;
        this.fareService = fareService;
    }

    public Path getPath(LoginMember loginMember, Long sourceStationId, Long targetStationId) {

        GraphPath graphPath =
                DijkstraShortestPath.findPathBetween(makeGraph(),
                        stationService.findStationById(sourceStationId),
                        stationService.findStationById(targetStationId));

        int distance = (int) graphPath.getWeight();
        List<Long> distinctLineIds = new Sections(graphPath.getEdgeList()).getDistinctLineIds();
        int fare = fareService.findFare(distinctLineIds, distance, loginMember);

        return new Path(graphPath.getVertexList(), distance, fare);
    }

    private WeightedMultigraph<Station, Section> makeGraph() {

        Sections sections = lineService.findAllSections();
        List<Station> stations = stationService.findAll();

        WeightedMultigraph<Station, Section> graph = new WeightedMultigraph(DefaultWeightedEdge.class);

        stations.forEach(graph::addVertex);
        sections.getSections().forEach(section ->
                graph.addEdge(section.getUpStation(), section.getDownStation(), section));

        return graph;
    }
}
