package subway.path.domain.fare;

import org.springframework.stereotype.Component;
import subway.line.domain.Line;
import subway.member.domain.LoginMember;

import java.util.List;

@Component
public class FarePolicyFactory {

    public FarePolicy createBy(int distance, List<Line> lines, LoginMember loginMember) {
        if (loginMember.isNotLogined()) {
            return getDefaultPolicy(distance, lines);
        }
        return getDefaultPolicy(distance, lines)
                .andThen(new AgePolicy(loginMember.getAge()));
    }

    private FarePolicy getDefaultPolicy(int distance, List<Line> lines) {
        return new DistancePolicy(distance)
                .andThen(new LinePolicy(lines));
    }
}
