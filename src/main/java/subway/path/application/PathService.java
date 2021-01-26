package subway.path.application;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.line.domain.Section;
import subway.member.domain.LoginMember;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;
import subway.station.dto.StationResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PathService {
    public static final int DEFAULT_FARE = 1250;
    public static final int DEFAULT_DISTANCE = 10;
    public static final int EXTRA_DISTANCE = 50;
    public static final int PER_DISTANCE1 = 5;
    public static final int PER_DISTANCE2 = 8;
    public static final int EXTRA_CHARGE = 100;
    public static final int CHILD_MIN_AGE = 6;
    public static final int TEENAGER_MAX_AGE = 18;
    public static final int CHILD_MAX_AGE = 12;
    public static final int DEDUCTION = 350;
    public static final double CHILD_DISCOUNT_RATE = 0.5;
    public static final double TEENAGER_DISCOUNT_RATE = 0.2;

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
        if (distance <= DEFAULT_DISTANCE) {
            return DEFAULT_FARE;
        }
        if (distance <= EXTRA_DISTANCE) {
            return DEFAULT_FARE + (distance - DEFAULT_DISTANCE + PER_DISTANCE1 - 1) / PER_DISTANCE1 * EXTRA_CHARGE;
        }
        return DEFAULT_FARE + (EXTRA_DISTANCE - DEFAULT_DISTANCE + PER_DISTANCE1 - 1) / PER_DISTANCE1 * EXTRA_CHARGE
                + (distance - EXTRA_DISTANCE + PER_DISTANCE2 - 1) / PER_DISTANCE2 * EXTRA_CHARGE;
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

    public int fareWithDiscount(PathResponse pathResponse, LoginMember loginMember) {
        int age = loginMember.getAge();
        int fare = pathResponse.getFare();

        if (age < CHILD_MIN_AGE) {
            return fare;
        }
        if (age <= CHILD_MAX_AGE) {
            return fare - (int) ((fare - DEDUCTION) * CHILD_DISCOUNT_RATE);
        }
        if (age <= TEENAGER_MAX_AGE) {
            return fare - (int) ((fare - DEDUCTION) * TEENAGER_DISCOUNT_RATE);
        }
        return fare;
    }
}
