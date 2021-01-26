package subway.path.domain;

import java.util.List;

import org.jgrapht.GraphPath;
import subway.path.exceptions.UnconnectedPathException;
import subway.station.domain.Station;

public class Path {
    private final int BASE_FARE = 1_250;

    private final GraphPath<Station, SubwayEdge> path;

    public Path(GraphPath<Station, SubwayEdge> shortestPath) {
        validatePath(shortestPath);
        this.path = shortestPath;
    }

    private void validatePath(GraphPath<Station, SubwayEdge> shortestPath) {
        if(shortestPath == null) {
            throw new UnconnectedPathException();
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
        if (isTeenager(age)) {
            fare = (int) Math.floor((fare - 350) * 0.8);
        }
        if (isChild(age)) {
            fare = (int) Math.floor((fare - 350) * 0.5);
        }
        return fare;
    }

    private int getMaxLineExtraFare() {
        return path.getEdgeList()
                .stream()
                .map(SubwayEdge::getFare)
                .max(Integer::compare)
                .get();
    }

    private int getDistanceExtraFare() {
        int distance = getDistance();
        if (distance > 50) {
            return 800 + (int) (Math.ceil((distance - 50) / 8.0d) * 100);
        }
        if (distance > 10) {
            return (int) ((Math.ceil((distance - 10) / 5.0d)) * 100);
        }
        return 0;
    }

    private boolean isTeenager(int age) {
        return age >= 13 && age < 19;
    }

    private boolean isChild(int age) {
        return age >= 6 && age < 13;
    }
}
