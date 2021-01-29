package subway.line.domain;

import subway.member.domain.LoginMember;
import subway.member.domain.LoginMemberType;
import subway.station.domain.Station;

import java.util.List;

public class DirectedSections {
    private static final int MIN_DISTANCE = 10;
    private static final int MAX_DISTANCE = 50;
    private static final int DEFAULT_PRICE = 1250;
    private static final int ADDITIONAL_PRICE = 100;
    private final ExtraFare maxExtraFare;
    private final int distance;
    private final List<Station> stations;

    public DirectedSections(List<Station> stations, int distance, ExtraFare maxExtraFare) {
        this.stations = stations;
        this.maxExtraFare = maxExtraFare;
        this.distance = distance;
    }

    public int getMaxExtraFare() {
        return maxExtraFare.getValue();
    }

    public int getPrice() {
        int distance = getDistance();
        if (distance <= MIN_DISTANCE) {
            return DEFAULT_PRICE + maxExtraFare.getValue();
        }
        if (distance <= MAX_DISTANCE) {
            return DEFAULT_PRICE +
                    under50Bonus(distance) * ADDITIONAL_PRICE +
                    maxExtraFare.getValue();
        }
        return DEFAULT_PRICE +
                (MAX_DISTANCE - MIN_DISTANCE) / 5 * ADDITIONAL_PRICE +
                over50Bonus(distance) * ADDITIONAL_PRICE +
                maxExtraFare.getValue();
    }

    public int under50Bonus(int distance) {
        return (distance - MIN_DISTANCE) / 5 + ((distance - MIN_DISTANCE) % 5 == 0 ? 0 : 1);
    }

    public int over50Bonus(int distance) {
        return (distance - MAX_DISTANCE) / 8 + ((distance - MIN_DISTANCE) % 8 == 0 ? 0 : 1);
    }

    public int getResultPrice(LoginMember loginMember) {
        int defaultPrice = getPrice();
        if (loginMember.getType() == LoginMemberType.KID) {
            return 0;
        }
        if (loginMember.getType() == LoginMemberType.CHILD) {
            return defaultPrice - (defaultPrice - 350) / 2;
        }
        if (loginMember.getType() == LoginMemberType.ADOLESCENT) {
            return defaultPrice - (defaultPrice - 350) / 5;
        }
        return defaultPrice;
    }

    public int getDistance() {
        return distance;
    }

    public List<Station> getStations(){
        return stations;
    }
}
