package subway.path.util;

import subway.line.domain.Line;
import subway.member.domain.LoginMember;

import java.util.List;

public class FareCalculator {

    private static final int BASE_FARE = 1250;

    public static int getFare(int distance, List<Line> lines, LoginMember loginMember) {
        int fare = BASE_FARE;

        fare += DistanceFare.getFare(distance);

        fare += LineFare.getFare(lines);

        if (loginMember.isLogined()) {
            fare = LoginMemberAgeFare.getFare(loginMember, fare);
        }
        return fare;
    }
}
