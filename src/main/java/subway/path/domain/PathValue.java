package subway.path.domain;

import subway.member.domain.LoginMember;
import subway.station.domain.Station;

import java.util.List;

public class PathValue {
    private List<Station> stations;
    private int distance;
    private int fare;

    public PathValue(List<Station> stations, int distance, int fare) {
        this.stations = stations;
        this.distance = distance;
        this.fare = fare;
    }

    public static PathValue of(Path path, LoginMember loginMember) {
        return new PathValue(path.getStations(), path.getDistance(), path.getFare(loginMember));
    }

    public List<Station> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }

    public int getFare() {
        return fare;
    }
}
