package subway.path.application;

import org.jgrapht.GraphPath;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.line.domain.Sections;
import subway.member.dao.MemberDao;
import subway.path.domain.Path;
import subway.path.domain.PathVertex;
import subway.path.domain.PathVertices;
import subway.path.dto.PathResult;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathService {
    MemberDao memberDao;
    LineDao lineDao;
    SectionDao sectionDao;

    public PathService(LineDao lineDao, SectionDao sectionDao, MemberDao memberDao){
        this.lineDao = lineDao;
        this.sectionDao = sectionDao;
        this.memberDao = memberDao;
    }

    public PathResult findShortestPath(Long sourceId, Long targetId, String email) {
        Sections sections = sectionDao.findAll();
        PathVertices pathVertices = new PathVertices();
        pathVertices.initAllVertex(lineDao.findAll());
        Path path = new Path(pathVertices, sections);

        GraphPath result = path.findShortestPath(sourceId, targetId);
        List<PathVertex> vertexList = result.getVertexList();
        int distance = (int) result.getWeight();
        int fare = path.calculateFare(
                distance,
                path.findLineIdListInPath(new PathVertices(vertexList))
                        .stream()
                        .map(lineId -> lineDao.findById(lineId).getExtraFare())
                        .collect(Collectors.toList()));

        if(email != null )
            fare = path.discount(memberDao.findByEmail(email).getAge(), fare);

        return new PathResult(new PathVertices(vertexList), distance, fare);

    }
}

