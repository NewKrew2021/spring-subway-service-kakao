package subway.path.application;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.domain.Line;
import subway.member.domain.LoginMember;
import subway.path.domain.FareCalculator;
import subway.path.domain.Path;
import subway.path.domain.StationGraph;
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

    public PathResponse findShortestPathResponse(LoginMember loginMember, Long source, Long target) {
        Path shortestPath = getShortestPath(loginMember, stationDao.findById(source), stationDao.findById(target));
        return PathResponse.of(shortestPath);
    }

    public Path getShortestPath(LoginMember loginMember, Station source, Station target) {
        List<Line> lines = lineDao.findAll();
        StationGraph stationGraph = new StationGraph(lines);
        DijkstraShortestPath dijkstraShortestPath = stationGraph.getDijkstraShortestPath();

        List<Station> stations = dijkstraShortestPath.getPath(source, target).getVertexList();
        int distance = (int) dijkstraShortestPath.getPathWeight(source, target);
        int fare = FareCalculator.calculate(distance, stations, lines, loginMember);

        return new Path(stations, distance, fare);
    }
}