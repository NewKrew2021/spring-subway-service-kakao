package subway.path.domain;

import org.jgrapht.GraphPath;
import subway.path.exceptions.InvalidPathException;
import subway.station.domain.Station;

import java.util.List;

public class Path {
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

    public int getFare(int age) {
        int fare = BASE_FARE
                + getMaxLineExtraFare()
                + DistanceFare.getExtraFareByDistance(getDistance());

        return AgeFare.getDiscountedFareByAge(fare, age);
    }

    private int getMaxLineExtraFare() {
        return path.getEdgeList()
                .stream()
                .map(SubwayEdge::getFare)
                .max(Integer::compare)
                .orElseThrow(InvalidPathException::new);
    }
}
