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
            lineDao.findAll() // List<Line>
                    .stream() // Line -> Sections, fare
                    .forEach(path::addEdges); // 여기다가 fare를 넣
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
            if( 19 > age && 13 <= age ) {
                fare = (int) ((fare - 350) * 0.8 + 350);
            }
            if( 13 > age && 6 <= age ) {
                fare = (int) ((fare - 350) * 0.5 + 350);
            }
            return this;
        }

        private int calculateFareByDistance(int distance) {
            if( distance > 50 ) {
                int ceil = (int) Math.ceil((distance - 50) / 8.0);
                return 1250 + 800 + ceil * 100;
            }
            if( distance > 10 ) {
                int ceil = (int) Math.ceil((distance - 10) / 5.0);
                return 1250 + ceil * 100;
            }
            return 1250;
        }

        public PathResponse makePathResponse() {
            List<StationResponse> stationResponses = graphPath.getVertexList().stream()
                    .map(StationResponse::of)
                    .collect(Collectors.toList());
            return new PathResponse(stationResponses, (int) graphPath.getWeight(), fare);
        }
    }

}
