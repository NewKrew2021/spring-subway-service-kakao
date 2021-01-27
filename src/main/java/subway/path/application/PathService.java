package subway.path.application;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.domain.Lines;
import subway.line.domain.Section;
import subway.member.domain.LoginMember;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;
import subway.station.dto.StationResponse;
import subway.util.FareUtil;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PathService {
    private StationDao stationDao;
    private LineDao lineDao;

    public PathService(LineDao lineDao, StationDao stationDao) {
        this.stationDao = stationDao;
        this.lineDao = lineDao;
    }

    public PathResponse findShortestPath(LoginMember loginMember, Long source, Long target) {
        Lines lines = new Lines(lineDao.findAll());

        DijkstraShortestPath dijkstraShortestPath = lines.getDijkstraShortestPath();
        int totalDistance = (int) dijkstraShortestPath.getPathWeight(source, target);
        List<Long> shortestPath = lines.getShortestPath(dijkstraShortestPath, source, target);
        List<Section> pathSections = lines.getShortestPathSections(shortestPath, stationDao.findAll());
        int totalFare = FareUtil.getTotalFare(loginMember,
                FareUtil.getMaxExtraFare(pathSections, lines.getLines()) + FareUtil.calculateDistanceFare(totalDistance));

        return new PathResponse(shortestPath.stream()
                .map(id -> StationResponse.of(stationDao.findById(id)))
                .collect(Collectors.toList()),
                totalDistance, totalFare);
    }
}
