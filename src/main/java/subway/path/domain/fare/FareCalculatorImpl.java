package subway.path.domain.fare;

import org.springframework.stereotype.Component;
import subway.line.domain.Line;
import subway.member.domain.LoginMember;

import java.util.List;

@Component
public class FareCalculatorImpl implements FareCalculator {

    private static final int INITIAL_FARE = 0;

    private final FarePolicyFactory farePolicyFactory;

    public FareCalculatorImpl(FarePolicyFactory farePolicyFactory) {
        this.farePolicyFactory = farePolicyFactory;
    }

    @Override
    public int getFare(int distance, List<Line> lines, LoginMember loginMember) {
        int fare = INITIAL_FARE;
        fare = farePolicyFactory.createBy(distance).apply(fare);
        fare = farePolicyFactory.createBy(lines).apply(fare);
        fare = farePolicyFactory.createBy(loginMember).apply(fare);

        return fare;
    }
}
