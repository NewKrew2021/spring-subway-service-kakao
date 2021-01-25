package subway.path.domain.fare;

import org.springframework.stereotype.Component;
import subway.line.domain.Line;
import subway.member.domain.LoginMember;

import java.util.List;

@Component
public class FareCalculatorImpl implements FareCalculator {

    private static final int BASE_FARE = 1250;

    private final FarePolicyFactory farePolicyFactory;

    public FareCalculatorImpl(FarePolicyFactory farePolicyFactory) {
        this.farePolicyFactory = farePolicyFactory;
    }

    @Override
    public int getFare(int distance, List<Line> lines, LoginMember loginMember) {
        return farePolicyFactory.createBy(distance, lines, loginMember).apply(BASE_FARE);
    }
}
