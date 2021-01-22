package subway.path.application;

import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.dao.SectionDao;
import subway.line.domain.Sections;
import subway.path.domain.Path;
import subway.path.domain.PathExplorer;
import subway.path.dto.PathResponse;
import subway.station.dao.StationDao;

@Service
public class PathService {
    private SectionDao sectionDao;
    private LineDao lineDao;
    private StationDao stationDao;

    public PathService(SectionDao sectionDao, LineDao lineDao, StationDao stationDao) {
        this.sectionDao = sectionDao;
        this.lineDao = lineDao;
        this.stationDao = stationDao;
    }

    public PathResponse findShortestPathResponse(Long source, Long target) {
        Sections sections = sectionDao.findAll();
        Path shortestPath = PathExplorer.getShortestPath(lineDao.findAll(), stationDao.findById(source), stationDao.findById(target));
        return PathResponse.of(shortestPath);
    }

    /*
    1) 최단 경로 Path에 있는 station 리스트 -> 현재 station, 다음 station을 up, down으로 하는 section 조회
    2) 해당 section을 가진 노선의 추가 요금 조회?
    3) 가장 큰 추가요금을 Path의 fare에 합산

Sections pathSections = sections.getPathSections(shortestPath);
        List<Line> lines = lineDao.findAll(); // 쿼리 새로 만들어서 fare만 가져오게 하는 방법
        int maxExtraFare = pathSections.getMaxExtraFare(lines);
        shortestPath.calculateFare(maxExtraFare);
     */
}