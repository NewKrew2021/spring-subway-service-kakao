package subway.path.domain;

import subway.member.domain.LoginMember;
import subway.path.util.FareUtil;
import subway.station.dto.StationResponse;

import java.util.List;

public class Path {

    private final List<StationResponse> stations;
    private final int distance;
    private final int extraFare;

    public Path(List<StationResponse> stations, int distance, int extraFare) {
        this.stations = stations;
        this.distance = distance;
        this.extraFare = extraFare;
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public int getDistance() {
        return distance;
    }

    public int getTotalFare() {
        return extraFare + FareUtil.calculateDistanceFare(distance);
    }

    public int getTotalFare(LoginMember loginMember) {
        return discountAmount(getTotalFare(), loginMember);
    }

    public int discountAmount(int totalFare, LoginMember loginMember) {
        return totalFare - (int) ((totalFare - 350) * loginMember.getDiscountPercentage());
    }
}
