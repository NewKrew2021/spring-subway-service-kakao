package subway.path.application;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.line.domain.Section;
import subway.member.domain.LoginMember;
import subway.path.domain.Fare;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;
import subway.station.dto.StationResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PathService {
    private final StationDao stationDao;
    private final SectionDao sectionDao;
    private final LineDao lineDao;

    public PathService(StationDao stationDao, SectionDao sectionDao, LineDao lineDao) {
        this.stationDao = stationDao;
        this.sectionDao = sectionDao;
        this.lineDao = lineDao;
    }

    public PathResponse searchShortestPath(Long source, Long target) {
        DijkstraShortestPath<Long, Integer> dijkstraShortestPath = new DijkstraShortestPath(createGraph());
        List<Long> shortestPath = dijkstraShortestPath.getPath(source, target).getVertexList();

        List<StationResponse> stationResponses = StationResponse.listOf(shortestPath.stream()
                .map(stationDao::findById)
                .collect(Collectors.toList()));
        int distance = (int) dijkstraShortestPath.getPathWeight(source, target);

        return new PathResponse(stationResponses, distance, getFareByDistance(distance) + getExtraFare(shortestPath));
    }

    private int getExtraFare(List<Long> path) {
        return getLineIds(path).stream().map(lineDao::findExtraFareById).reduce(0, Integer::max);
    }

    private Set<Long> getLineIds(List<Long> path) {
        Set<Long> lineIds = new HashSet<>();
        for (int i = 1; i < path.size(); ++i) {
            lineIds.add(sectionDao.findLineIdByUpStationIdAndDownStationId(path.get(i - 1), path.get(i)));
        }
        return lineIds;
    }

    protected int getFareByDistance(int distance) {
        return Fare.getFareByDistance(distance);
    }

    public int fareWithDiscount(PathResponse pathResponse, LoginMember loginMember) {
        return Fare.applyDiscount(loginMember.getAge(), pathResponse.getFare());
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
