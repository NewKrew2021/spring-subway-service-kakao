package subway.path.domain.fare;

import org.springframework.stereotype.Component;
import subway.line.domain.Line;
import subway.member.domain.LoginMember;

import java.util.List;

@Component
public class FarePolicyFactory {

    public FarePolicy createBy(int distance, List<Line> lines, LoginMember loginMember) {
        FarePolicy defaultPolicy = getDistanceAndLinePolicy(distance, lines);

        if (loginMember.isNotLogined()) {
            return defaultPolicy;
        }
        return defaultPolicy.andThen(new AgePolicy(loginMember.getAge()));
    }

    private FarePolicy getDistanceAndLinePolicy(int distance, List<Line> lines) {
        return new DistancePolicy(distance)
                .andThen(new LinePolicy(lines));
    }
}
