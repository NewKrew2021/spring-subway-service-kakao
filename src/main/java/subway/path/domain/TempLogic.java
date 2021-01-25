package subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.member.domain.LoginMember;
import subway.path.domain.fareStrategy.DistanceStrategy;
import subway.path.domain.fareStrategy.LineStrategy;
import subway.path.domain.fareStrategy.MemberStrategy;
import subway.station.domain.Station;

import java.util.List;


public class TempLogic {
    private final int INITIAL_FARE = 1250;

    private LoginMember loginMember;
    private ShortestGraph shortestGraph;

    public TempLogic (ShortestGraph shortestGraph, LoginMember loginMember) {
        this.shortestGraph = shortestGraph;
        this.loginMember = loginMember;
    }

    public List<Station> getStations() {
        return shortestGraph.getGraphPath().getVertexList();
    }

    public int getDistance() {
        return (int) shortestGraph.getGraphPath().getWeight();
    }

    public int getFare() {
        int fare = INITIAL_FARE;
        fare = new DistanceStrategy(fare, shortestGraph.getGraphPath()).getFare();
        fare = new LineStrategy(fare, shortestGraph.getGraphPath()).getFare();
        if (loginMember != null) {
            fare = new MemberStrategy(fare, loginMember).getFare();
        }
        return fare;
    }
}
