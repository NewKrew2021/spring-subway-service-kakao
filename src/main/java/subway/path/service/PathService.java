package subway.path.service;

import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.path.domain.PathBuilder;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;

@Service
public class PathService {

    private final StationDao stationDao;
    private final LineDao lineDao;

    public PathService(StationDao stationDao, LineDao lineDao) {
        this.stationDao = stationDao;
        this.lineDao = lineDao;
    }

    public PathResponse findPathResponse(long sourceId, long targetId, int age) {
        return new PathBuilder()
                .initializePath(stationDao.findAll(), lineDao.findAll())
                .makeGraphPath(sourceId, targetId)
                .calculateFare()
                .discountByAge(age)
                .makePathResponse();
    }
}
