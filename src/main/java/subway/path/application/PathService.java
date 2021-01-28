package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.member.dao.MemberDao;
import subway.path.domain.Fare;
import subway.path.domain.Path;
import subway.path.domain.PathVertices;
import subway.path.dto.PathResult;

import java.util.ArrayList;
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

    public PathResult calculatePathResult(Long sourceId, Long targetId, int age) {
        PathVertices pathVertices = new PathVertices();
        List<Line> lineList = lineDao.findAll();
        pathVertices.initAllVertex(lineList);

        List<Section> sectionList = new ArrayList<>();
        lineList.stream()
                .map(line -> line.getSections().getSections())
                .forEach(sectionList::addAll);

        Path path = new Path(pathVertices, new Sections(sectionList));

        PathResult result = path.findShortestPath(sourceId, targetId);

        List<Integer> lineExtraFareList = path.findLineIdListInPath(result.getPathVertices())
                .stream()
                .map(lineId -> lineDao.findById(lineId).getExtraFare())
                .collect(Collectors.toList());

        result.setFare(Fare.calculateFare(result.getDistance(), lineExtraFareList, age));
        return result;
    }
}

