package subway.path.domain;


import subway.member.domain.LoginMember;
import subway.path.domain.farePolicy.DistancePolicy;
import subway.path.domain.farePolicy.FarePolicy;
import subway.path.domain.farePolicy.LinePolicy;
import subway.path.domain.farePolicy.MemberPolicy;

public class Fare {
    private final int INITIAL_FARE = 1250;

    private int fare;

    public Fare(ShortestGraph shortestGraph, LoginMember loginMember) {
        this.fare = makeFare(shortestGraph, loginMember);
    }

    private int makeFare(ShortestGraph shortestGraph, LoginMember loginMember) {
        int fare = INITIAL_FARE;
        fare = applyFareStrategy(new DistancePolicy(fare, shortestGraph.getGraphPath()));
        fare = applyFareStrategy(new LinePolicy(fare, shortestGraph.getGraphPath()));
        fare = applyFareStrategy(new MemberPolicy(fare, loginMember));
        return fare;
    }

    private int applyFareStrategy(FarePolicy farePolicy){
        return farePolicy.getFare();
    }

    public int getFare() {
        return fare;
    }

}
