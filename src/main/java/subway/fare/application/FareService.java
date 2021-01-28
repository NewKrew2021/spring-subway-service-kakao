package subway.fare.application;

import org.springframework.stereotype.Service;
import subway.fare.domain.AgeFarePolicy;
import subway.fare.domain.DistanceFarePolicy;
import subway.fare.domain.LineFarePolicy;
import subway.line.application.LineService;
import subway.member.domain.AnonymousMember;
import subway.member.domain.LoginMember;

import java.util.List;
import java.util.Objects;

@Service
public class FareService {

    public static final int DEFAULT_FARE = 1250;

    private LineService lineService;

    public FareService(LineService lineService) {
        this.lineService = lineService;
    }

    public int findFare(List<Long> lineIds, int distance, LoginMember loginMember) {

        int fare = DEFAULT_FARE;

        fare += LineFarePolicy.findLineExtraFare(lineService.findExtraFares(lineIds));

        fare += DistanceFarePolicy.findDistanceFare(distance);

        if (!(loginMember instanceof AnonymousMember)) {
            fare = AgeFarePolicy.applyAgeDiscount(fare, loginMember.getAge());
        }

        return fare;
    }
}