package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.member.dao.MemberDao;
import subway.member.domain.AGE;
import subway.path.domain.Path;
import subway.path.domain.PathVertices;
import subway.path.dto.PathResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

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

    public PathResult findShortestPath(Long sourceId, Long targetId, int age) {

        //Sections sections = sectionDao.findAll();
        PathVertices pathVertices = new PathVertices();
        List<Line> lineList = lineDao.findAll();
        pathVertices.initAllVertex(lineList);

        List<Section> sectionList = new ArrayList<>();
        lineList.stream()
                .map(line -> line.getSections().getSections())
                .forEach(sectionList::addAll);

        Path path = new Path(pathVertices, new Sections(sectionList));

        PathResult result = path.findShortestPath(sourceId, targetId);

        int basicFare = path.getBasicFare(result.getDistance());

        List<Long> lineIdList = path.findLineIdListInPath(result.getPathVertices());
        int extraFare = calculateExtraFare(lineIdList);

        result.setFare(discount(age, basicFare + extraFare));
        return result;

    }

    private int calculateExtraFare(List<Long> lineIdList){
        return lineIdList.stream().map(lineId -> lineDao.findById(lineId).getExtraFare()).max(Integer::compare).orElse(0);
    }

    private int discount(int age, int fare){
        AGE ageStatus = AGE.getAgeStatus(age);
        return (int)((fare - ageStatus.getDeduction()) * ageStatus.getSaleRate()) + ageStatus.getDeduction();
    }

}

