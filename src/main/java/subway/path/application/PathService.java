package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.path.domain.StationGraph;
import subway.path.dto.PathResponse;
import subway.station.domain.Station;

@Service
public class PathService {
    final LineDao lineDao;

    public PathService(LineDao lineDao) {
        this.lineDao = lineDao;
    }

    public PathResponse getPathInfo(Station source, Station target, int age) {
        StationGraph stationGraph = new StationGraph(lineDao.findAll());
        return PathResponse.of(stationGraph.getPathInfo(source, target, age));
    }
}

