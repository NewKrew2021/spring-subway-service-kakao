package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.domain.Line;
import subway.member.domain.LoginMember;
import subway.path.domain.fare.FareCalculator;
import subway.path.domain.path.Path;
import subway.path.domain.path.PathExplorer;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;

import java.util.List;

@Service
public class PathService {
	private final int ADULT_AGE = 20;
	private LineDao lineDao;
	private StationDao stationDao;

	public PathService(LineDao lineDao, StationDao stationDao) {
		this.lineDao = lineDao;
		this.stationDao = stationDao;
	}

	public PathResponse findShortestPathResponse(LoginMember loginMember, Long source, Long target) {
		List<Line> lines = lineDao.findAll();
		PathExplorer pathExplorer = new PathExplorer(lines);
		
		Path shortestPath = pathExplorer.getShortestPath(stationDao.findById(source), stationDao.findById(target));
		
		List<Line> containedLines = shortestPath.getContainedLines(lines);
		FareCalculator fareCalculator = new FareCalculator(containedLines);
		int age = loginMember == null ? ADULT_AGE : loginMember.getAge();
		int fare = fareCalculator.calculate(shortestPath, age);

		return PathResponse.of(shortestPath, fare);
	}
}