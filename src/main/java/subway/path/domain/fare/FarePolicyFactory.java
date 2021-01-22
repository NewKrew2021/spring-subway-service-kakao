package subway.path.domain.fare;

import org.springframework.stereotype.Component;
import subway.line.domain.Line;
import subway.member.domain.LoginMember;

import java.util.List;

@Component
public class FarePolicyFactory {

    public FarePolicy createBy(int distance) {
        return new DistancePolicy(distance);
    }

    public FarePolicy createBy(List<Line> lines) {
        return new LinePolicy(lines);
    }

    public FarePolicy createBy(LoginMember loginMember) {
        if (loginMember.isNotLogined()) {
            return new IdentityPolicy();
        }
        return new AgePolicy(loginMember.getAge());
    }
}
