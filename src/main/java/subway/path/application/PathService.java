package subway.path.application;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Autowired
    public PathService(StationService stationService, LineService lineService) {
        this.stationService = stationService;
        this.lineService = lineService;
    }

    public PathResponse getPath(LoginMember loginMember, Long sourceStationId, Long targetStationId) {

        WeightedMultigraph<Station, Section> graph = makeGraph();

        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(graph);


        GraphPath graphPath = dijkstraShortestPath.getPath(
                stationService.findStationById(sourceStationId),
                stationService.findStationById(targetStationId)
        );

        int fare = 1250;

        Sections sections = new Sections(graphPath.getEdgeList());

        fare += findExtraFare(sections);

        fare += findDistanceFare((int) graphPath.getWeight());

        if (loginMember != null) {
            fare = applyAgeDiscount(fare, loginMember.getAge());
        }

        Path path = new Path(graphPath.getVertexList(), (int) graphPath.getWeight(), fare);

        return PathResponse.of(path);
    }

    private int applyAgeDiscount(int fare, int age) {
        if (age < 6) {
            return 0;
        } else if (age < 13) {
            return (int) ((fare - 350) * 0.5);
        } else if (age < 19) {
            return (int) ((fare - 350) * 0.8);
        }

        return fare;
    }

    private int findDistanceFare(int distance) {
        if (distance <= 10) {
            return 0;
        } else if (distance <= 50) {
            return ((distance - 11) / 5 + 1) * 100;
        }

        return 800 + ((distance - 51) / 8 + 1) * 100;
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

    private int findExtraFare(Sections sections) {
        int maxExtraFare = 0;

        for (Section section : sections.getSections()) {
            Long lineId = section.getLindId();
            maxExtraFare = Integer.max(maxExtraFare, lineService.findExtraFare(lineId));
        }

        return maxExtraFare;
    }
}
