package subway.path.application;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.domain.Line;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PathService {
    private StationDao stationDao;
    private LineDao lineDao;

    public PathService(LineDao lineDao, StationDao stationDao) {
        this.stationDao = stationDao;
        this.lineDao = lineDao;
    }

    public PathResponse findMinDistance(Long source, Long target) {
        return dijkstra(source, target);
    }

    private PathResponse dijkstra(Long source, Long target) {
        List<Line> lines = lineDao.findAll();
        WeightedMultigraph<Long, DefaultWeightedEdge> graph
                = new WeightedMultigraph(DefaultWeightedEdge.class);

        lines.forEach(line -> line.getStations()
                .forEach(station -> graph.addVertex(station.getId())));

        lines.forEach(line -> line.getSections().getSections()
                .forEach(section -> graph.setEdgeWeight(graph.addEdge(section.getUpStation().getId(),
                        section.getDownStation().getId()), section.getDistance())));

        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(graph);
        List<Long> shortestPath = dijkstraShortestPath.getPath(source, target).getVertexList();
        int pathWeight = (int) dijkstraShortestPath.getPathWeight(source, target);

        return new PathResponse(shortestPath.stream()
                .map(id -> StationResponse.of(stationDao.findById(id))).collect(Collectors.toList()),
                pathWeight, 0);

    }

}
