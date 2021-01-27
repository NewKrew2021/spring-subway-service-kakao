package subway.path.domain.fare;

import subway.member.domain.LoginMember;

public class FareStrategyFactory {
    public static FareStrategy create(int distance, int extraFare, LoginMember loginMember) {
        if (loginMember.isLoggedout()) {
            return new GeneralFareStrategy(distance, extraFare);
        }
        return new LoginMemberFareStrategy(distance, extraFare, loginMember);
    }
}
