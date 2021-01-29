package subway.path.domain;

import subway.member.domain.LoginMember;

public class DefaultFareStrategy implements FareStrategy{
    @Override
    public int getTotalFare(LoginMember loginMember, int totalFare) {
        return totalFare;
    }
}
