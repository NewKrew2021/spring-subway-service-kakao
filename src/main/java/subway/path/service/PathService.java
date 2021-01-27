package subway.path.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.line.dao.SectionDao;
import subway.path.domain.Path;
import subway.path.domain.Person;
import subway.path.domain.SubwayGraph;
import subway.station.dao.StationDao;
import subway.station.domain.Station;

@Service
public class PathService {
    private final SectionDao sectionDao;
    private final StationDao stationDao;
    private static SubwayGraph subwayGraph;
    public static boolean isUpdated = false;

    public static void newlyUpdated() {
        synchronized (PathService.class) {
            isUpdated = false;
        }
    }

    @Autowired
    public PathService(SectionDao sectionDao, StationDao stationDao) {
        this.sectionDao = sectionDao;
        this.stationDao = stationDao;
    }

    public Path find(long sourceId, long targetId) {
        synchronized (PathService.class) {
            if (!isUpdated) {
                subwayGraph = new SubwayGraph(sectionDao.getSectionEdges());
                isUpdated = true;
            }
        }

        Station sourceStation = stationDao.findById(sourceId);
        Station targetStation = stationDao.findById(targetId);

        return subwayGraph.getShortestPath(sourceStation, targetStation);
    }

    public int calculateFare(Path path, Person person) {
        int fare = path.calculateFare();
        return fare - ageDiscount(fare, person);
    }

    private int ageDiscount(int fare, Person person) {
        return (int) ((fare - person.getDeduction()) * (person.getDiscountPercentage() / 100.0));
    }
}
