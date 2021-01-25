package subway.path.application;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.member.domain.LoginMember;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;
import subway.station.dto.StationResponse;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PathService {
    public static final int DEFAULT_FARE = 1250;
    public static final int DEFAULT_DISTANCE = 10;
    public static final int EXTRA_DISTANCE = 50;
    public static final int PER_DISTANCE1 = 5;
    public static final int PER_DISTANCE2 = 8;
    public static final int EXTRA_CHARGE = 100;
    private final StationDao stationDao;
    private final SectionDao sectionDao;
    private final LineDao lineDao;

    public PathService(StationDao stationDao, SectionDao sectionDao, LineDao lineDao) {
        this.stationDao = stationDao;
        this.sectionDao = sectionDao;
        this.lineDao = lineDao;
    }

    public PathResponse searchShortestPath(Long source, Long target) {
        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(createGrapth());
        List<String> shortestPath = dijkstraShortestPath.getPath(String.valueOf(source), String.valueOf(target)).getVertexList();
        int extraFare = getExtraFare(shortestPath);

        List<StationResponse> stationResponses = StationResponse.listOf(shortestPath.stream()
                .map(id -> stationDao.findById(Long.parseLong(id)))
                .collect(Collectors.toList()));
        int distance = (int) dijkstraShortestPath.getPathWeight(String.valueOf(source), String.valueOf(target));

        return new PathResponse(stationResponses, distance, extraFare + getFareByDistance(distance));
    }

    private int getExtraFare(List path) {
        Set<Long> lineIds = getLineIds(path);
        return lineIds.stream().map(lineDao::findExtraFareById).reduce(0, Integer::max);
    }

    private Set<Long> getLineIds(List<String> path) {
        Set<Long> lineIds = new HashSet<>();
        for (int i = 1; i < path.size(); ++i) {
            lineIds.add(sectionDao.findLineIdByUpStationIdAndDownStationId(Long.valueOf(path.get(i - 1)), Long.valueOf(path.get(i))));
        }
        return lineIds;
    }

    private int getFareByDistance(int distance) {
        if (distance <= DEFAULT_DISTANCE) {
            return DEFAULT_FARE;
        }
        if (distance <= EXTRA_DISTANCE) {
            return DEFAULT_FARE + (distance - DEFAULT_DISTANCE + PER_DISTANCE1 - 1) / PER_DISTANCE1 * EXTRA_CHARGE;
        }
        return DEFAULT_FARE + (EXTRA_DISTANCE - DEFAULT_DISTANCE + PER_DISTANCE1 - 1) / PER_DISTANCE1 * EXTRA_CHARGE + (distance - EXTRA_DISTANCE + PER_DISTANCE2 - 1) / PER_DISTANCE2 * EXTRA_CHARGE;
    }

    private WeightedMultigraph<String, DefaultWeightedEdge> createGrapth() {
        WeightedMultigraph<String, DefaultWeightedEdge> graph = new WeightedMultigraph(DefaultWeightedEdge.class);
        List<Map<String, Object>> sections = sectionDao.findAllAsMap();

        for (Map<String, Object> section : sections) {
            String upStationId = String.valueOf(section.get("up_station_id"));
            String downStationId = String.valueOf(section.get("down_station_id"));

            graph.addVertex(upStationId);
            graph.addVertex(downStationId);
            graph.setEdgeWeight(graph.addEdge(upStationId, downStationId), (int) section.get("distance"));
        }

        return graph;
    }

    public PathResponse discount(PathResponse pathResponse, LoginMember loginMember) {
        int age = loginMember.getAge();
        if (age < 6 || age >= 19) {
            return pathResponse;
        }

        int fare = pathResponse.getFare();
        if (age < 13) {
            return new PathResponse(pathResponse.getStations(), pathResponse.getDistance(), fare - (int) ((fare - 350) * 0.5));
        }
        return new PathResponse(pathResponse.getStations(), pathResponse.getDistance(), fare - (int) ((fare - 350) * 0.2));
    }
}
