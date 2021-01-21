package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.dao.SectionDao;
import subway.path.domain.Path;
import subway.path.domain.SubwayGraph;
import subway.station.dao.StationDao;
import subway.station.domain.Station;

@Service
public class PathService {
    private final SectionDao sectionDao;
    private final StationDao stationDao;
    private SubwayGraph subwayGraph;
    public static boolean isUpdated = false;

    public PathService(SectionDao sectionDao, StationDao stationDao) {
        this.sectionDao = sectionDao;
        this.stationDao = stationDao;
    }

    public Path find(long sourceId, long targetId) {
        if (!isUpdated) {
            subwayGraph = new SubwayGraph(sectionDao.getSections());
        }

        Station sourceStation = stationDao.findById(sourceId);
        Station targetStation = stationDao.findById(targetId);

        return subwayGraph.getShortestPath(sourceStation, targetStation);
    }
}
