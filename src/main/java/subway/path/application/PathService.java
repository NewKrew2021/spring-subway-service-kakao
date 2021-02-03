package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.path.domain.*;
import subway.path.dto.Fare;
import subway.path.dto.PathResponse;
import subway.path.dto.PathResult;
import subway.station.dao.StationDao;

import java.util.List;

@Service
public class PathService {
    LineDao lineDao;
    StationDao stationDao;
    Path path;

    public PathService(LineDao lineDao, StationDao stationDao, Path path){
        this.lineDao = lineDao;
        this.stationDao = stationDao;
        this.path = path;
    }

    public PathResponse findShortestPath(Long sourceId, Long targetId, Integer age) {
        path.initPath(lineDao.findAll());
        PathResult result = path.findShortestPath(stationDao.findById(sourceId), stationDao.findById(targetId));
        List<Integer> extraFares = result.getPathVertices().getExtraFareList(path.getLines());
        Fare fare = new FareCalculator().calculate(result, extraFares, age);

        return new PathResponse(result, fare);

    }
}

