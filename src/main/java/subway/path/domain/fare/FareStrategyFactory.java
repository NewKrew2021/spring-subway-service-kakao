package subway.path.domain.fare;

import subway.common.domain.Distance;
import subway.common.domain.Fare;
import subway.member.domain.LoginMember;

public class FareStrategyFactory {
    public static FareStrategy create(Distance distance, Fare extraFare, LoginMember loginMember) {
        if (loginMember != null) {
            return new LoginMemberFareStrategy(distance, extraFare, loginMember);
        }
        return new DefaultFareStrategy(distance, extraFare);
    }
}
