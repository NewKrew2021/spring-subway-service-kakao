package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.member.domain.LoginMember;
import subway.path.dto.PathDto;
import subway.path.util.AgeGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FareService {
    public static final int DEFAULT_FARE = 1250;
    public static final int EXTRA1_LOWER_BOUND = 10;
    public static final int EXTRA2_LOWER_BOUND = 50;
    public static final double UNIT_OF_DISTANCE_FOR_EXTRA1 = 5.0;
    public static final double UNIT_OF_DISTANCE_FOR_EXTRA2 = 8.0;
    public static final int EXTRA_CHARGE = 100;

    private final LineDao lineDao;
    private final SectionDao sectionDao;

    public FareService(LineDao lineDao, SectionDao sectionDao) {
        this.lineDao = lineDao;
        this.sectionDao = sectionDao;
    }

    public int getFare(PathDto pathDto, LoginMember loginMember) {
        int fare = getDistanceFare(pathDto.getDistance()) + getExtraFare(pathDto.getShortestPath());
        if (loginMember == null) {
            return fare;
        }
        return discount(fare, loginMember.getAge());
    }

    protected int getDistanceFare(int distance) {
        int extraDistance2 = Math.max(0, distance - EXTRA2_LOWER_BOUND);
        int extraDistance1 = Math.max(0, distance - extraDistance2 - EXTRA1_LOWER_BOUND);

        return DEFAULT_FARE
                + getDistanceFarePerRange(extraDistance1, UNIT_OF_DISTANCE_FOR_EXTRA1)
                + getDistanceFarePerRange(extraDistance2, UNIT_OF_DISTANCE_FOR_EXTRA2);
    }

    private int getDistanceFarePerRange(int distance, double unitOfDistance) {
        return (int) Math.ceil(distance / unitOfDistance) * EXTRA_CHARGE;
    }

    private int getExtraFare(List<Long> path) {
        return lineDao.findMaxExtraFareByIds(getLineIds(path));
    }

    protected List<Long> getLineIds(List<Long> path) {
        Set<Long> lineIds = new HashSet<>();
        for (int i = 1; i < path.size(); ++i) {
            lineIds.add(sectionDao.findLineIdByUpStationIdAndDownStationId(path.get(i - 1), path.get(i)));
        }
        return new ArrayList<>(lineIds);
    }

    protected int discount(int fare, int age) {
        AgeGroup ageGroup = AgeGroup.getAgeGroup(age);
        return fare - (int) ((fare - AgeGroup.getDeduction(ageGroup)) * AgeGroup.getDiscountRate(ageGroup));
    }
}
