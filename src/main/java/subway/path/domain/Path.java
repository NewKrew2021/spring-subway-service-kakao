package subway.path.domain;

import subway.station.domain.Station;

import java.util.List;

public class Path {
    private final List<Station> stations;
    private final int distance;
    private int fare;

    public Path(List<Station> stations, int distance, int fare) {
        this.stations = stations;
        this.distance = distance;
        this.fare = fare;
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

    public void applyDistanceFarePolicy() {
        DistanceFare distanceFare = DistanceFare.getDistanceFare(distance);
        fare += distanceFare.calculateExtraFareByDistance(distance);
    }

    public void applyAgeFarePolicy(int age) {
        AgeFare ageFare = AgeFare.getAgeFare(age);
        fare -= ageFare.calculateDiscountFareByAge(fare);
    }
}
