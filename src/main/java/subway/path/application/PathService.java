package subway.path.application;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Service;
import subway.line.dao.SectionDao;
import subway.line.domain.Section;
import subway.member.domain.LoginMember;
import subway.path.dto.PathDto;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathService {
    private final StationDao stationDao;
    private final SectionDao sectionDao;
    private final FareService fareService;

    public PathService(StationDao stationDao, SectionDao sectionDao, FareService fareService) {
        this.stationDao = stationDao;
        this.sectionDao = sectionDao;
        this.fareService = fareService;
    }

    public PathResponse searchPathAndFare(LoginMember loginMember, Long source, Long target) {
        PathDto pathDto = searchShortestPath(source, target);
        int fare = fareService.getFare(pathDto, loginMember);
        return new PathResponse(getStationResponses(pathDto.getShortestPath()),
                pathDto.getDistance(), fare);
    }

    private List<StationResponse> getStationResponses(List<Long> path) {
        return StationResponse.listOf(path.stream()
                .map(stationDao::findById)
                .collect(Collectors.toList()));
    }

    private PathDto searchShortestPath(Long source, Long target) {
        DijkstraShortestPath<Long, Integer> dijkstraShortestPath = new DijkstraShortestPath(createGraph());
        List<Long> shortestPath = dijkstraShortestPath.getPath(source, target).getVertexList();
        int distance = (int) dijkstraShortestPath.getPathWeight(source, target);
        return new PathDto(shortestPath, distance);
    }

    private WeightedMultigraph<Long, DefaultWeightedEdge> createGraph() {
        WeightedMultigraph<Long, DefaultWeightedEdge> graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);
        List<Section> sections = sectionDao.findAll();

        for (Section section : sections) {
            Long upStationId = section.getUpStation().getId();
            Long downStationId = section.getDownStation().getId();

            graph.addVertex(upStationId);
            graph.addVertex(downStationId);
            graph.setEdgeWeight(graph.addEdge(upStationId, downStationId), section.getDistance());
        }

        return graph;
    }
}
