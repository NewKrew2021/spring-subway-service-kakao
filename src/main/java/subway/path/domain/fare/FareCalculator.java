package subway.path.domain.fare;

import subway.line.domain.Line;
import subway.member.domain.LoginMember;

import java.util.List;

public interface FareCalculator {

    int getFare(int distance, List<Line> lines, LoginMember loginMember);
}
