package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.domain.Line;
import subway.path.domain.Graph;
import subway.path.dto.PathResponse;
import subway.station.domain.Station;

import java.util.List;

@Service
public class PathService {
    final LineDao lineDao;

    public PathService(LineDao lineDao) {
        this.lineDao = lineDao;
    }

    public PathResponse getShortestPathOfStations(String startStationId, String endStationId) {
        List<Line> lines = lineDao.findAll();
        Graph graph = new Graph(lines);
        return PathResponse.of(graph.getPath(startStationId, endStationId));
    }
}

