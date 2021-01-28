package subway.path.domain;


import subway.line.domain.Line;
import subway.member.domain.LoginMember;
import subway.path.domain.farePolicy.*;

import java.util.List;
import java.util.stream.Collectors;


public class Fare {
    private int fare;

    public Fare(ShortestGraph shortestGraph, LoginMember loginMember) {
        this.fare = makeSubwayFare(shortestGraph, loginMember);
    }

    private int makeSubwayFare(ShortestGraph shortestGraph, LoginMember loginMember) {
        BasicFare fare = new SubwayFare();
        fare = new DistancePolicy(fare, (int) shortestGraph.getGraphPath().getWeight());
        fare = new LinePolicy(fare, extractLines(shortestGraph));
        fare = new MemberPolicy(fare, loginMember);
        return fare.getFare();
    }

    private List<Line> extractLines(ShortestGraph shortestGraph) {
        List<WeightWithLine> weightWithLines = shortestGraph.getGraphPath().getEdgeList();
        return weightWithLines.stream().map(WeightWithLine::getLine)
                .collect(Collectors.toList());
    }

    public int getFare() {
        return fare;
    }

}
