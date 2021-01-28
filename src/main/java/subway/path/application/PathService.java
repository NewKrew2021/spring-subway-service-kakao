package subway.path.application;

import org.jgrapht.GraphPath;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.line.domain.Line;
import subway.line.domain.Sections;
import subway.member.dao.MemberDao;
import subway.path.domain.Fare;
import subway.path.domain.Path;
import subway.path.domain.PathVertex;
import subway.path.domain.PathVertices;
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

    public PathResponse findShortestPath(Long sourceId, Long targetId, String email) {
        List<Line> lines = lineDao.findAll();
        PathVertices pathVertices = PathVertices.from(lines);
        Path path = new Path(pathVertices);
        lines.forEach(line -> path.addSections(line.getSections()));

        PathResult result = path.findShortestPath(stationDao.findById(sourceId), stationDao.findById(targetId));
        Fare fare = new Fare(result.getDistance(),
                path.findLineIdListInPath(result.getPathVertices())
                        .stream()
                        .map(lineId -> lineDao.findById(lineId).getExtraFare())
                        .collect(Collectors.toList()));

        if(email != null )
            fare.discount(memberDao.findByEmail(email).getAge());

        return new PathResponse(result, fare);

    }
}

