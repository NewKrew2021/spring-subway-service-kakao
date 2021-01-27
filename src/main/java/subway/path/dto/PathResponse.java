package subway.path.dto;

import subway.member.domain.LoginMember;
import subway.path.domain.Path;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

import java.util.List;

public class PathResponse {
    private List<StationResponse> stations;
    private int distance;
    private int fare;

    public PathResponse() {
    }

    public PathResponse(List<Station> stations, int distance, int fare) {
        this.stations = StationResponse.listOf(stations);
        this.distance = distance;
        this.fare = fare;
    }

    public static PathResponse of(Path path) {
        return new PathResponse(path.getStations(), path.getDistance(), path.getTotalFare());
    }

    public static PathResponse of(Path path, LoginMember loginMember) {
        return new PathResponse(path.getStations(), path.getDistance(), path.getTotalFare(loginMember));
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }

    public int getFare() {
        return fare;
    }
}
