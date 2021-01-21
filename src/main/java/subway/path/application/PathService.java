package subway.path.application;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.line.dao.SectionDao;
import subway.line.domain.Sections;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathService {
    private final SectionDao sectionDao;
    private final StationDao stationDao;

    @Autowired
    public PathService(SectionDao sectionDao, StationDao stationDao) {
        this.sectionDao = sectionDao;
        this.stationDao = stationDao;
    }

    public PathResponse getShortPath(Long sourceId, Long targetId) {
        Sections sections = new Sections(sectionDao.getSections());
        DijkstraShortestPath dijkstraShortestPath = sections.getDijkstraShortestPath();
        List<Long> vertexList = dijkstraShortestPath.getPath(sourceId, targetId).getVertexList();
        return PathResponse.of(vertexList.stream()
                .map(stationId -> StationResponse.of(stationDao.findById(stationId)))
                .collect(Collectors.toList()), (int) dijkstraShortestPath.getPathWeight(sourceId, targetId));
    }
}
