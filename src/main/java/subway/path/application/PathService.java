package subway.path.application;

import org.jgrapht.GraphPath;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
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
    SectionDao sectionDao;
    StationDao stationDao;

    public PathService(LineDao lineDao, SectionDao sectionDao, MemberDao memberDao, StationDao stationDao){
        this.lineDao = lineDao;
        this.sectionDao = sectionDao;
        this.memberDao = memberDao;
        this.stationDao = stationDao;
    }

    public PathResponse findShortestPath(Long sourceId, Long targetId, String email) {
        Sections sections = sectionDao.findAll();
        PathVertices pathVertices = PathVertices.from(lineDao.findAll());
        Path path = new Path(pathVertices, sections);

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

