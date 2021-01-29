package subway.path.domain;


import subway.line.domain.Line;
import subway.member.domain.LoginMember;
import subway.path.domain.fareStrategy.*;

import java.util.List;
import java.util.stream.Collectors;

public class Fare {
    private final int INITIAL_FARE = 1250;
    private static final int TEENAGER_MIN_AGE = 13;
    private static final int TEENAGER_MAX_AGE = 19;
    private static final int CHILD_MIN_AGE = 6;

    private int fare;

    public Fare(int fare) {
        this.fare = fare;
    }

    public Fare(ShortestGraph shortestGraph, LoginMember loginMember) {
        this.fare = makeFare(shortestGraph, loginMember);
    }

    public int makeFare(ShortestGraph shortestGraph, LoginMember loginMember) {
        int fare = INITIAL_FARE;
        List<WeightWithLine> weightWithLines = shortestGraph.getGraphPath().getEdgeList();
        List<Line> lines = weightWithLines.stream()
                .map(WeightWithLine::getLine)
                .collect(Collectors.toList());

        fare = new DefaultFareStrategy(fare,
                (int)shortestGraph.getGraphPath().getWeight(),
                lines).getFare();
        if (loginMember.equals(LoginMember.NOT_LOGIN_MEMBER)) {
            return fare;
        }

        if (loginMember.getAge() >= CHILD_MIN_AGE && loginMember.getAge() < TEENAGER_MIN_AGE){
            fare = new ChildFareStrategy(fare).getFare();
        }

        if (loginMember.getAge() >= TEENAGER_MIN_AGE && loginMember.getAge() < TEENAGER_MAX_AGE){
            fare = new TeenagerFareStrategy(fare).getFare();
        }

        return fare;
    }

    public int getFare() {
        return fare;
    }
}
