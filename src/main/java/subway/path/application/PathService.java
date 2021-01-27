package subway.path.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.domain.Line;
import subway.member.domain.LoginMember;
import subway.path.domain.KakaoMap;
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

    public PathResponse getPathAndFare(LoginMember loginMember, Long source, Long target) {
        List<Line> lines = lineDao.findAll();
        ComplimentaryAge complimentaryAge = ComplimentaryAge.ADULT;
        if (loginMember != null) {
            complimentaryAge = ComplimentaryAge.getAgeGroup(loginMember.getAge());
        }
        KakaoMap kakaoMap = new KakaoMap(lines, stationDao.findById(source), stationDao.findById(target), complimentaryAge);
        return new PathResponse(kakaoMap.getShortestPath().stream()
                .map(StationResponse::of)
                .collect(Collectors.toList()),
                kakaoMap.getTotalDistance(), kakaoMap.getTotalFare());
    }
}
