package subway.path.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.domain.Line;
import subway.member.domain.LoginMember;
import subway.path.domain.MetroNavigator;
import subway.path.dto.PathResponse;
import subway.path.domain.ComplimentaryAge;
import subway.station.dao.StationDao;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class PathService {
    private StationDao stationDao;
    private LineDao lineDao;

    public PathService(LineDao lineDao, StationDao stationDao) {
        this.stationDao = stationDao;
        this.lineDao = lineDao;
    }

    public PathResponse getPathAndFare(LoginMember loginMember, Long startStationId, Long destStationId) {
        List<Line> lines = lineDao.findAll();
        ComplimentaryAge complimentaryAge = ComplimentaryAge.ADULT;
        if (loginMember != null) {
            complimentaryAge = ComplimentaryAge.getAgeGroup(loginMember.getAge());
        }
        MetroNavigator metroNavigator = new MetroNavigator(lines, stationDao.findById(startStationId), stationDao.findById(destStationId), complimentaryAge);
        return new PathResponse(metroNavigator.getShortestPath().stream()
                .map(StationResponse::of)
                .collect(Collectors.toList()),
                metroNavigator.getTotalDistance(), metroNavigator.getTotalFare());
    }
}
