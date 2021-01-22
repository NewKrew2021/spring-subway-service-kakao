package subway.path.application;

import org.jgrapht.GraphPath;
import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.domain.Line;
import subway.path.domain.Path;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;

@Service
public class PathService {

    private final StationDao stationDao;
    private final LineDao lineDao;

    public PathService(StationDao stationDao, LineDao lineDao) {
        this.stationDao = stationDao;
        this.lineDao = lineDao;
    }

    public PathResponse findPathResponse(long sourceId, long targetId) {
        return new PathBuilder()
                .makePath()
                .initializePath()
                .makeGraphPath(sourceId, targetId)
                .makePathResponse();
    }

    private class PathBuilder {

        private final Path path = new Path();
        private GraphPath graphPath;

        public PathBuilder makePath() {
            return this;
        }

        public PathBuilder initializePath() {
            path.addStations(stationDao.findAll());
            lineDao.findAll()
                    .stream()
                    .map(Line::getSections)
                    .forEach(path::addEdges);
            return this;
        }

        public PathBuilder makeGraphPath(long sourceId, long targetId) {
            graphPath = path.findShortestPathGraph(stationDao.findById(sourceId), stationDao.findById(targetId));
            return this;
        }

        public PathResponse makePathResponse() {
            return new PathResponse(graphPath.getVertexList(), (int) graphPath.getWeight());
        }
    }

}
