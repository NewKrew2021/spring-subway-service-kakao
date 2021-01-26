package subway.path.domain;

import org.jgrapht.GraphPath;
import subway.path.exceptions.NotFoundPathException;
import subway.station.domain.Station;

import java.util.List;

public class Path {

    public static final int TEENAGER_AGE_UPPERBOUND = 19;
    public static final int TEENAGER_AGE_LOWERBOUND = 13;
    public static final int CHILD_AGE_LOWERBOUND = 6;
    public static final int FREE_FARE = 0;
    public static final int BASE_DEDUCT_FARE = 350;
    public static final double TEENAGER_DISCOUNT_RATE = 0.2;
    public static final double CHILD_DISCOUNT_RATE = 0.5;
    public static final int ADDITIONAL_FARE_BY_DISTANCE = 100;
    public static final int FIRST_DISTANCE_SECTION = 10;
    public static final int SECOND_DISTANCE_SECTION = 50;
    public static final double FIRST_DISTANCE_UNIT = 5.0d;
    public static final double SECOND_DISTANCE_UNIT = 8.0d;
    public static final int FIRST_DISTANCE_SECTION_ADDITIONAL_FARE = 800;
    public static final int NO_ADDITIONAL_FARE = 0;
    public static final int BASE_FARE = 1250;

    GraphPath<Station, SubwayEdge> path;

    public Path(GraphPath<Station, SubwayEdge> shortestPath) {
        this.path = shortestPath;
    }

    public List<Station> getStations() {
        return path.getVertexList();
    }

    public Integer getDistance() {
        return (int) path.getWeight();
    }

    public int getMaxLineExtraFare() {
        return path.getEdgeList()
                .stream()
                .map(SubwayEdge::getFare)
                .max(Integer::compare)
                .orElseThrow(NotFoundPathException::new);
    }

    public int getFare(int age) {
        int fare = BASE_FARE + getMaxLineExtraFare() + getDistanceAdditionalFare();

        if (isTeenager(age)) {
            fare -= (int) Math.floor((fare - BASE_DEDUCT_FARE) * TEENAGER_DISCOUNT_RATE);
        }

        if (isChild(age)) {
            fare -= (int) Math.floor((fare - BASE_DEDUCT_FARE) * CHILD_DISCOUNT_RATE);
        }

        if (isInfant(age)) {
            fare = FREE_FARE;
        }

        return fare;
    }

    private boolean isTeenager(int age) {
        return age >= TEENAGER_AGE_LOWERBOUND && age < TEENAGER_AGE_UPPERBOUND;
    }

    private boolean isChild(int age) {
        return age >= CHILD_AGE_LOWERBOUND && age < TEENAGER_AGE_LOWERBOUND;
    }

    private boolean isInfant(int age) {
        return age < CHILD_AGE_LOWERBOUND;
    }

    private int getDistanceAdditionalFare() {
        int distance = getDistance();

        if (distance > SECOND_DISTANCE_SECTION) {
            return FIRST_DISTANCE_SECTION_ADDITIONAL_FARE +
                    getAdditionalFare(distance - SECOND_DISTANCE_SECTION, SECOND_DISTANCE_UNIT);
        }

        if (distance > FIRST_DISTANCE_SECTION) {
            return getAdditionalFare(distance - FIRST_DISTANCE_SECTION, FIRST_DISTANCE_UNIT);
        }

        return NO_ADDITIONAL_FARE;
    }

    private int getAdditionalFare(int distance, double distanceUnit) {
        return ((int) Math.ceil(distance / distanceUnit)) * ADDITIONAL_FARE_BY_DISTANCE;
    }
}
