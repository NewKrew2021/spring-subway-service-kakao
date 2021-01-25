package subway.path.domain;

import subway.member.domain.LoginMember;
import subway.path.util.FareUtil;
import subway.station.dto.StationResponse;

import java.util.List;

public class Path {

    public static final int DEDUCTIONS = 350;
    private List<StationResponse> stations;
    private int distance;
    private int extraFare;

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

    public int getExtraFare() {
        return extraFare;
    }

    public int getTotalFare() {
        int totalFare = extraFare + FareUtil.calculateDistanceFare(distance);
        return totalFare;
    }

    public int getTotalFare(LoginMember loginMember) {
        return discountAmount(getTotalFare(), loginMember);
    }

    public int discountAmount(int totalFare, LoginMember loginMember) {
        return totalFare - (int) ((totalFare - DEDUCTIONS) * loginMember.getDiscountPercentage());
    }
}
