package subway.station.application;

import org.springframework.stereotype.Service;
import subway.exception.InvalidStationException;
import subway.station.dao.StationDao;
import subway.station.domain.Station;
import subway.station.dto.StationRequest;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {
    private StationDao stationDao;

    public StationService(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    public Station saveStation(StationRequest stationRequest) {
        return stationDao.insert(stationRequest.toStation());
    }

    public Station findStationById(Long id) {
        return stationDao.findById(id)
                .filter(station -> station.hasSameId(id))
                .orElseThrow(InvalidStationException::new);
    }

    public List<Station> findAllStationResponses() {
        return stationDao.findAll();
    }

    public void deleteStationById(Long id) {
        stationDao.deleteById(id);
    }

    public List<Station> findStations() {
        return stationDao.findAll();
    }
}
