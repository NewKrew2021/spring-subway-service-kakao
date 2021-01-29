package subway.path.application;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.path.domain.Fare;
import subway.path.domain.Path;
import subway.station.dao.StationDao;

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

    public Path searchShortestPath(Long source, Long target) {
        DijkstraShortestPath<Long, Integer> dijkstraShortestPath = new DijkstraShortestPath(createGraph());
        List<Long> shortestPath = dijkstraShortestPath.getPath(source, target).getVertexList();
        int distance = (int) dijkstraShortestPath.getPathWeight(source, target);

        return new Path(
                shortestPath.stream()
                        .map(stationDao::findById)
                        .collect(Collectors.toList()),
                distance,
                Fare.getExtraFare(getDistinctLines(shortestPath)) + Fare.getFareByDistance(distance)
        );
    }

    private List<Line> getDistinctLines(List<Long> path) {
        Set<Long> lineIds = new HashSet<>();
        for (int i = 1; i < path.size(); ++i) {
            lineIds.add(sectionDao.findLineIdByUpStationIdAndDownStationId(path.get(i - 1), path.get(i)));
        }

        return lineIds.stream()
                .map(lineDao::findById)
                .collect(Collectors.toList());
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
