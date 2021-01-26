package subway.path.domain;

import subway.line.domain.Line;
import subway.member.domain.LoginMember;
import subway.station.domain.Station;

import java.util.List;

public class Path {
    List<Line> lines;
    LoginMember loginMember;
    ShortestPath shortestPath;

    public Path(List<Line> lines, Station source, Station target) {
        this.lines = lines;
        this.shortestPath = new ShortestPath(lines, source, target);
    }

    public Path(List<Line> lines, Station source, Station target, LoginMember loginMember) {
        this(lines,source,target);
        this.loginMember = loginMember;
    }

    public List<Station> getShortestPath() {
        return shortestPath.getShortestPathStations();
    }

    public int getTotalFare() {
        return 0;
    }

    public int getTotalDistance() {
        return shortestPath.getTotalDistance();
    }
}
