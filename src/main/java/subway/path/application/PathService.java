package subway.path.application;

import org.jgrapht.GraphPath;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.path.domain.Path;
import subway.path.domain.PathGraphEdge;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathService {

    private final static int BASE_FARE_DEDUCTION = 350;
    private final static int BASE_FARE = 1250;
    private final static int DISTANCE_FARE = 100;
    private final static double DISCOUNT_TEEN = 0.8;
    private final static double DISCOUNT_CHILD = 0.5;
    private final static int DISTANCE_UNDER = 10;
    private final static int DISTANCE_UPPER = 50;
    private final static double DISCOUNT_PER_DISTANCE_UNDER = 5.0;
    private final static double DISCOUNT_PER_DISTANCE_UPPER = 8.0;
    private final static int TEEN_AGE_TOP = 19;
    private final static int TEEN_AGE_BOTTOM = 13;
    private final static int CHILD_AGE_BOTTOM = 6;
    private final static int SURCHARGE_DISTANCE_UNDER = 800;

    private final StationDao stationDao;
    private final LineDao lineDao;

    public PathService(StationDao stationDao, LineDao lineDao) {
        this.stationDao = stationDao;
        this.lineDao = lineDao;
    }

    public PathResponse findPathResponse(long sourceId, long targetId, int age) {
        return new PathBuilder()
                .initializePath()
                .makeGraphPath(sourceId, targetId)
                .calculateFare()
                .discountBy(age)
                .makePathResponse();
    }

    private class PathBuilder {

        private final Path path = new Path();
        private GraphPath<Station, PathGraphEdge> graphPath;
        private int fare;

        public PathBuilder initializePath() {
            path.addStations(stationDao.findAll());
            lineDao.findAll()
                    .stream()
                    .forEach(path::addEdges);
            return this;
        }

        public PathBuilder makeGraphPath(long sourceId, long targetId) {
            graphPath = path.findShortestPathGraph(stationDao.findById(sourceId), stationDao.findById(targetId));
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

        private PathBuilder discountBy(int age) {
            if( TEEN_AGE_TOP > age && TEEN_AGE_BOTTOM <= age ) {
                fare = (int) ((fare - BASE_FARE_DEDUCTION) * DISCOUNT_TEEN + BASE_FARE_DEDUCTION);
            }
            if( TEEN_AGE_BOTTOM > age && CHILD_AGE_BOTTOM <= age ) {
                fare = (int) ((fare - BASE_FARE_DEDUCTION) * DISCOUNT_CHILD + BASE_FARE_DEDUCTION);
            }
            return this;
        }

        private int calculateFareByDistance(int distance) {
            if( distance > DISTANCE_UPPER ) {
                int ceil = (int) Math.ceil((distance - DISTANCE_UPPER) / DISCOUNT_PER_DISTANCE_UPPER);
                return BASE_FARE + SURCHARGE_DISTANCE_UNDER + ceil * DISTANCE_FARE;
            }
            if( distance > DISTANCE_UNDER ) {
                int ceil = (int) Math.ceil((distance - DISTANCE_UNDER) / DISCOUNT_PER_DISTANCE_UNDER);
                return BASE_FARE + ceil * DISTANCE_FARE;
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

}
