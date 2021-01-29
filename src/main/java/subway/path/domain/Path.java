package subway.path.domain;

import subway.member.domain.LoginMember;
import subway.station.domain.Station;

import java.util.List;

public class Path {
    private final List<Station> path;
    private final int distance;
    private final int fare;

    public Path(List<Station> path, int distance, int fare) {
        this.path = path;
        this.distance = distance;
        this.fare = fare;
    }

    public List<Station> getStations() {
        return path;
    }

    public int getDistance() {
        return distance;
    }

    public int getFare() {
        return fare;
    }

    public Path discountedPath(LoginMember loginMember) {
        return new Path(path, distance, Fare.applyDiscount(loginMember.getAge(), fare));
    }
}
