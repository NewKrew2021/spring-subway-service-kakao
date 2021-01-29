package subway.path.domain;

import subway.member.domain.LoginMember;

public interface FareStrategy {
    int DEDUCTION_FARE = 350;
    double TEENAGER_SALE_RATE = 0.2;
    double CHILD_SALE_RATE = 0.5;

    int getTotalFare(LoginMember loginMember, int totalFare);
}
