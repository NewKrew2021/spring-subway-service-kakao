package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.dao.SectionDao;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.path.domain.Path;
import subway.path.dto.PathResult;
import subway.station.dao.StationDao;
import subway.station.domain.Station;

import java.util.List;

@Service
public class PathService {

    StationDao stationDao;
    SectionDao sectionDao;

    public PathService(StationDao stationDao, SectionDao sectionDao){
        this.stationDao = stationDao;
        this.sectionDao = sectionDao;
    }

    public PathResult findShortestPath(Long sourceId, Long targetId) {
        List<Station> stations = stationDao.findAll();
        Sections sections = sectionDao.findAll();
        return new Path(stations, sections)
                .findShortestPath(stationDao.findById(sourceId), stationDao.findById(targetId));
    }
}

