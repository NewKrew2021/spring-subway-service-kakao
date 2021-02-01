package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.domain.Lines;
import subway.member.domain.LoginMember;
import subway.path.domain.Path;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;
import subway.station.dto.StationResponse;

import java.util.stream.Collectors;


@Service
public class PathService {
    private StationDao stationDao;
    private LineDao lineDao;

    public PathService(LineDao lineDao, StationDao stationDao) {
        this.stationDao = stationDao;
        this.lineDao = lineDao;
    }

    public PathResponse findMinDistance(LoginMember loginMember, Long source, Long target) {
        Path path = new Path(new Lines(lineDao.findAll()));
        int totalDistance = path.getTotalDistance(source, target);
        int totalFare = path.getAgeFare(loginMember, path.getMaxExtraFare(source, target, stationDao.findAll())
                 + path.calculateDistanceFare(source, target));

        return new PathResponse(path.getShortestPath(source,target).stream()
                .map(id -> StationResponse.of(stationDao.findById(id)))
                .collect(Collectors.toList()),
                totalDistance, totalFare);
    }
}
