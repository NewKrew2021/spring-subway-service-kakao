package subway.path.domain;


import subway.member.domain.LoginMember;
import subway.path.domain.fareStrategy.DistanceStrategy;
import subway.path.domain.fareStrategy.FareStrategy;
import subway.path.domain.fareStrategy.LineStrategy;
import subway.path.domain.fareStrategy.MemberStrategy;

public class Fare {
    private final int INITIAL_FARE = 1250;

    private int fare;

    public Fare(int fare) {
        this.fare = fare;
    }

    public Fare(ShortestGraph shortestGraph, LoginMember loginMember) {
        this.fare = makeFare(shortestGraph, loginMember);
    }


    public int makeFare(ShortestGraph shortestGraph, LoginMember loginMember) {
        int fare = INITIAL_FARE;
        fare = new DistanceStrategy(fare, shortestGraph.getGraphPath()).getFare();
        fare = new LineStrategy(fare, shortestGraph.getGraphPath()).getFare();
        fare = new MemberStrategy(fare, loginMember).getFare();
        return fare;
    }

    public int getFare() {
        return fare;
    }
}
