package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.domain.Line;
import subway.member.domain.LoginMember;
import subway.path.domain.Path;
import subway.path.domain.ShortestPathExplorer;
import subway.path.domain.SubwayGraph;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;
import subway.station.domain.Station;

import java.util.List;

@Service
public class PathService {
    private LineDao lineDao;
    private StationDao stationDao;

    public PathService(LineDao lineDao, StationDao stationDao) {
        this.lineDao = lineDao;
        this.stationDao = stationDao;
    }

    public PathResponse findShortestPathResponse(LoginMember loginMember, Long sourceId, Long targetId) {
        List<Line> lines = lineDao.findAll();
        SubwayGraph subwayGraph = new SubwayGraph(lines);
        ShortestPathExplorer shortestPathExplorer = new ShortestPathExplorer(subwayGraph);

        Station source = stationDao.findById(sourceId);
        Station target = stationDao.findById(targetId);
        Path path = shortestPathExplorer.exploreShortestPath(lines, source, target);

        path.applyDistanceFarePolicy();
        if (loginMember != null) {
            path.applyAgeFarePolicy(loginMember.getAge());
        }

        return PathResponse.of(path);
    }
}