package subway.path.application;

import org.jgrapht.GraphPath;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.line.domain.Line;
import subway.line.domain.Sections;
import subway.member.dao.MemberDao;
import subway.path.domain.*;
import subway.path.dto.PathResponse;
import subway.path.dto.PathResult;
import subway.station.dao.StationDao;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathService {
    MemberDao memberDao;
    LineDao lineDao;
    StationDao stationDao;

    public PathService(LineDao lineDao, MemberDao memberDao, StationDao stationDao){
        this.lineDao = lineDao;
        this.memberDao = memberDao;
        this.stationDao = stationDao;
    }

    public PathResponse findShortestPath(Long sourceId, Long targetId, Integer age) {
        List<Line> lines = lineDao.findAll();
        Path path = new Path(lines);

        PathResult result = path.findShortestPath(stationDao.findById(sourceId), stationDao.findById(targetId));
        Fare fare = FareCalculator.calculate(result,
                path.findLineListInPath(result.getPathVertices())
                .stream()
                .map(line -> line.getExtraFare())
                .collect(Collectors.toList()),
                age);

        return new PathResponse(result, fare);

    }
}

