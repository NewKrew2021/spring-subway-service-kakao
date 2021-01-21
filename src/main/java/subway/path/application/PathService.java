package subway.path.application;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Service;
import subway.line.dao.SectionDao;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PathService {
    private final StationDao stationDao;
    private final SectionDao sectionDao;

    public PathService(StationDao stationDao, SectionDao sectionDao) {
        this.stationDao = stationDao;
        this.sectionDao = sectionDao;
    }

    public PathResponse searchShortestPath(Long source, Long target) {
        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(createGrapth());
        List<String> shortestPath = dijkstraShortestPath.getPath(String.valueOf(source), String.valueOf(target)).getVertexList();

        List<StationResponse> stationResponses = StationResponse.listOf(shortestPath.stream()
                .map(id -> stationDao.findById(Long.parseLong(id)))
                .collect(Collectors.toList()));
        double distance = dijkstraShortestPath.getPathWeight(String.valueOf(source), String.valueOf(target));

        return new PathResponse(stationResponses, (int) distance, 0);
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
}
