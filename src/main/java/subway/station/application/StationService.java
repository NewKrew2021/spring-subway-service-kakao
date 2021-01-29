package subway.station.application;

import org.springframework.stereotype.Service;
import subway.station.dao.StationDao;
import subway.station.domain.Station;

import java.util.List;

@Service
public class StationService {

    private StationDao stationDao;

    public StationService(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    public Station saveStation(Station station) {
        return stationDao.insert(station);
    }

    public Station findStationById(Long id) {
        return stationDao.findById(id);
    }

    public List<Station> findAll() {
        return stationDao.findAll();
    }

    public void deleteStationById(Long id) {
        stationDao.deleteById(id);
    }
}
