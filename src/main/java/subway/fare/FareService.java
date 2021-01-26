package subway.fare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.line.application.LineService;
import subway.member.domain.LoginMember;

import java.util.List;
import java.util.Objects;

@Service
public class FareService {

    public static final int DEFAULT_FARE = 1250;

    private LineService lineService;

    @Autowired
    public FareService(LineService lineService) {
        this.lineService = lineService;
    }

    public int findFare(List<Long> lineIds, int distance, LoginMember loginMember) {

        int fare = DEFAULT_FARE;

        fare += LineFarePolicy.findLineExtraFare(lineService.findExtraFares(lineIds));

        fare += DistanceFarePolicy.findDistanceFare(distance);

        if (!Objects.isNull(loginMember)) {
            fare = AgeFarePolicy.applyAgeDiscount(fare, loginMember.getAge());
        }

        return fare;
    }
}