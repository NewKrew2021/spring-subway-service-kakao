package subway.path.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.domain.Line;
import subway.member.domain.LoginMember;
import subway.path.domain.Path;
import subway.path.dto.PathResponse;
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

    public PathResponse getPathAndFare(LoginMember loginMember, Long source, Long target) {
        List<Line> lines = lineDao.findAll();
        Path path = new Path(lines, stationDao.findById(source), stationDao.findById(target), loginMember);
        return new PathResponse(path.getShortestPath().stream()
                .map(StationResponse::of)
                .collect(Collectors.toList()),
                path.getTotalDistance(), path.getTotalFare());
    }
}
