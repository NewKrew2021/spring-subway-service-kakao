package subway.path.domain;

import subway.line.domain.Line;
import subway.member.domain.LoginMember;

import java.util.List;

public class FareCalculator {


    public static int getFare(int distance, List<Line> lines, LoginMember loginMember) {
        int fare = DistanceFare.getFare((int) distance);

        fare += LineFare.getFare(lines);

        if (!loginMember.equals(LoginMember.NOT_LOGINED)) {
            fare = LoginMemberAgeFare.getFare(loginMember, fare);
        }
        return fare;
    }
}
