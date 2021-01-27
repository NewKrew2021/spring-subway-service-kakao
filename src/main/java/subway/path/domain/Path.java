package subway.path.domain;

import java.util.List;

import org.jgrapht.GraphPath;
import subway.path.exceptions.InvalidPathException;
import subway.station.domain.Station;

public class Path {
    private final int BASE_FARE = 1_250;
    private final int NON_DISCOUNTABLE_BASE_FARE = 350;
    private final int FARE_PER_DISTANCE = 100;
    private final int MIDDLE_DISTANCE_LIMIT = 10;
    private final int FAR_DISTANCE_LIMIT = 50;
    private final double FARE_INCREASE_MIDDLE_DISTANCE = 5d;
    private final double FARE_INCREASE_FAR_DISTANCE = 8d;

    private final GraphPath<Station, SubwayEdge> path;

    public Path(GraphPath<Station, SubwayEdge> shortestPath) {
        validatePath(shortestPath);
        this.path = shortestPath;
    }

    private void validatePath(GraphPath<Station, SubwayEdge> shortestPath) {
        if(shortestPath == null) {
            throw new InvalidPathException();
        }
    }

    public List<Station> getStations() {
        return path.getVertexList();
    }

    public Integer getDistance() {
        return (int) path.getWeight();
    }

    public int getFare(int age) {
        int fare = BASE_FARE + getMaxLineExtraFare() + getDistanceExtraFare();
        AgeGroup ageGroup = AgeGroup.getAgeGroup(age);
        if(ageGroup == AgeGroup.TODDLER) {
            return 0;
        }
        return (int) Math.floor((fare - NON_DISCOUNTABLE_BASE_FARE) * ageGroup.getFareRate() + NON_DISCOUNTABLE_BASE_FARE);
    }

    private int getMaxLineExtraFare() {
        return path.getEdgeList()
                .stream()
                .map(SubwayEdge::getFare)
                .max(Integer::compare)
                .orElseThrow(InvalidPathException::new);
    }

    private int getDistanceExtraFare() {
        int distance = getDistance();
        if (distance > FAR_DISTANCE_LIMIT) {
            return calculateFarDistanceExtraFare(distance);
        }
        if (distance > MIDDLE_DISTANCE_LIMIT) {
            return calculateMiddleDistanceExtraFare(distance);
        }
        return 0;
    }

    private int calculateFarDistanceExtraFare(int distance) {
        return (int) (Math.ceil((distance - FAR_DISTANCE_LIMIT) / FARE_INCREASE_FAR_DISTANCE) * FARE_PER_DISTANCE)
                + calculateMiddleDistanceExtraFare(FAR_DISTANCE_LIMIT);
    }

    private int calculateMiddleDistanceExtraFare(int distance) {
        return (int) ((Math.ceil((distance - MIDDLE_DISTANCE_LIMIT) / FARE_INCREASE_MIDDLE_DISTANCE)) * FARE_PER_DISTANCE);
    }
}
