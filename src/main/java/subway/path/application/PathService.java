package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.line.domain.Sections;
import subway.member.dao.MemberDao;
import subway.member.domain.AGE;
import subway.path.domain.Path;
import subway.path.domain.PathVertices;
import subway.path.dto.PathResult;
import java.util.List;

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

        PathResult result = path.findShortestPath(sourceId, targetId);

        int basicFare = path.getBasicFare(result.getDistance());

        List<Long> lineIdList = path.findLineIdListInPath(result.getPathVertices());
        int extraFare = calculateExtraFare(lineIdList);

        if(email == null){
            result.setFare(basicFare + extraFare);
            return result;
        }
        result.setFare(discount(memberDao.findByEmail(email).getAge(), basicFare + extraFare));
        return result;

    }

    private int calculateExtraFare(List<Long> lineIdList){
        return lineIdList.stream().map(lineId -> lineDao.findById(lineId).getExtraFare()).max(Integer::compare).orElse(0);
    }

//    - 청소년: 13세 이상~19세 미만
//    - 어린이: 6세 이상~ 13세 미만
//    청소년: 운임에서 350원을 공제한 금액의 20%할인
//    어린이: 운임에서 350원을 공제한 금액의 50%할인
//    6세 미만: 무임
    private int discount(int age, int fare){
        AGE ageStatus = AGE.getAgeStatus(age);
        return (int)((fare - ageStatus.getDeduction()) * ageStatus.getSaleRate()) + ageStatus.getDeduction();
    }

}

