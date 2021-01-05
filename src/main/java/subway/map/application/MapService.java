package subway.map.application;

import org.springframework.stereotype.Service;
import subway.line.dao.LineDao;
import subway.line.domain.Line;
import subway.line.dto.LineResponse;
import subway.map.dto.MapResponse;
import subway.station.dao.StationDao;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

import java.util.List;

@Service
public class MapService {
    private StationDao stationDao;
    private LineDao lineDao;

    public MapService(StationDao stationDao, LineDao lineDao) {
        this.stationDao = stationDao;
        this.lineDao = lineDao;
    }

    public MapResponse retrieveMap() {
        List<Station> stations = stationDao.findAll();
        List<Line> lines = lineDao.findAll();

        return new MapResponse(StationResponse.listOf(stations), LineResponse.listOf(lines));
    }
}
