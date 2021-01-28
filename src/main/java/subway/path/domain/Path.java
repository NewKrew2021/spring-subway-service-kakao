package subway.path.domain;

import subway.member.domain.LoginMember;
import subway.path.util.FareUtil;
import subway.station.domain.Station;

import java.util.List;

public class Path {

    public static final int DEDUCTIONS = 350;
    private final List<Station> stations;
    private final int distance;
    private final int extraFare;

    public Path(List<Station> stations, int distance, int extraFare) {
        this.stations = stations;
        this.distance = distance;
        this.extraFare = extraFare;
    }

    public List<Station> getStations() {
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
        return totalFare - (int) ((totalFare - DEDUCTIONS) * loginMember.getDiscountPercentage());
    }
}
