package subway.path.domain;

import subway.line.domain.Line;
import subway.member.domain.LoginMember;
import subway.station.domain.Station;

import java.util.List;

public class Path {
    private List<Station> stations;
    private int distance;
    private Fare fare;

    public Path(List<Line> lines, Station sourceStation, Station targetStation, LoginMember loginMember) {
        ShortestGraph shortestGraph = new ShortestGraph(lines, sourceStation, targetStation);
        this.stations = shortestGraph.getGraphPath().getVertexList();
        this.distance = (int) shortestGraph.getGraphPath().getWeight();
        this.fare = new Fare(shortestGraph, loginMember);
    }

    public int getDistance() {
        return distance;
    }

    public int getFare() {
        return fare.getFare();
    }

    public List<Station> getStations() {
        return stations;
    }

}
