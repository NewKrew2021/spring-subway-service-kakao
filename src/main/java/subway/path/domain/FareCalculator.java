package subway.path.domain;

import subway.line.domain.Line;
import subway.member.domain.LoginMember;

import java.util.List;

public class FareCalculator {

    public static int getFare(int distance, List<Line> lines, LoginMember loginMember) {
        int fare = DistanceFare.getFare(distance);

        fare += LineFare.getFare(lines);

        if (loginMember.isLogined()) {
            fare = LoginMemberAgeFare.getFare(loginMember, fare);
        }
        return fare;
    }
}
