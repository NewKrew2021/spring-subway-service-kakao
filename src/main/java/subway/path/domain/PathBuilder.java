package subway.path.domain;

import org.jgrapht.GraphPath;
import subway.line.domain.Line;
import subway.path.dto.PathResponse;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

public class PathBuilder {

    public static final int STUDENT_UPPER_BOUND = 19;
    public static final int STUDENT_LOWER_BOUND = 13;
    public static final int CHILD_LOWER_BOUND = 6;
    public static final int CHILD_UPPER_BOUND = 13;
    public static final int FIXED_DISCOUNT_FARE = 350;
    public static final double STUDENT_DISCOUNT_RATIO = 0.8;
    public static final double CHILD_DISCOUNT_RATIO = 0.5;
    public static final int BASE_FARE = 1250;
    public static final double UNIT_DISTANCE_OF_LONG_RANGE = 8.0;
    public static final double UNIT_DISTANCE_OF_SHORT_RANGE = 5.0;
    public static final int STANDARD_OF_LONG_RANGE = 50;
    public static final int STANDARD_OF_SHORT_RANGE = 10;
    public static final int UNIT_FARE = 100;
    public static final int MAX_FARE_OF_SHORT_RANGE = 800;

    private final Path path = new Path();
    private GraphPath<Station, PathGraphEdge> graphPath;
    protected List<Station> stations;
    protected int fare;

    public PathBuilder initializePath(List<Station> stations, List<Line> lines) {
        this.stations = stations;
        path.addStations(stations);
        lines.forEach(path::addEdges);
        return this;
    }

    public PathBuilder makeGraphPath(long sourceId, long targetId) {
        Station sourceStation = stations.stream()
                .filter(station -> station.getId() == sourceId)
                .findFirst()
                .get();
        Station targetStation = stations.stream()
                .filter(station -> station.getId() == targetId)
                .findFirst()
                .get();
        graphPath = path.findShortestPathGraph(sourceStation, targetStation);
        return this;
    }

    public PathBuilder calculateFare() {
        List<PathGraphEdge> edgeList = graphPath.getEdgeList();
        fare = edgeList.stream()
                .map(PathGraphEdge::getExtraFare)
                .max(Integer::compare)
                .orElse(0);

        int distance = (int) graphPath.getWeight();
        fare += calculateFareByDistance(distance);

        return this;
    }

    public PathBuilder discountByAge(int age) {
        if (STUDENT_LOWER_BOUND <= age && age < STUDENT_UPPER_BOUND) {
            fare = (int) ((fare - FIXED_DISCOUNT_FARE) * STUDENT_DISCOUNT_RATIO + FIXED_DISCOUNT_FARE);
        }
        if (CHILD_LOWER_BOUND <= age && age < CHILD_UPPER_BOUND) {
            fare = (int) ((fare - FIXED_DISCOUNT_FARE) * CHILD_DISCOUNT_RATIO + FIXED_DISCOUNT_FARE);
        }
        return this;
    }

    private int calculateFareByDistance(int distance) {
        if (distance > STANDARD_OF_LONG_RANGE) {
            int count = (int) Math.ceil((distance - STANDARD_OF_LONG_RANGE) / UNIT_DISTANCE_OF_LONG_RANGE);
            return BASE_FARE + MAX_FARE_OF_SHORT_RANGE + count * UNIT_FARE;
        }
        if (distance > STANDARD_OF_SHORT_RANGE) {
            int count = (int) Math.ceil((distance - STANDARD_OF_SHORT_RANGE) / UNIT_DISTANCE_OF_SHORT_RANGE);
            return BASE_FARE + count * UNIT_FARE;
        }
        return BASE_FARE;
    }

    public PathResponse makePathResponse() {
        List<StationResponse> stationResponses = graphPath.getVertexList().stream()
                .map(StationResponse::of)
                .collect(Collectors.toList());
        return new PathResponse(stationResponses, (int) graphPath.getWeight(), fare);
    }
}
