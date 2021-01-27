package subway.station.application;

import org.springframework.stereotype.Service;
import subway.station.dao.StationDao;
import subway.station.domain.Station;
import subway.station.dto.StationRequest;
import subway.station.dto.StationResponse;
import subway.station.exception.DuplicateStationNameException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {
    private final int NOT_DUPLICATED=0;
    private StationDao stationDao;

    public StationService(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    public StationResponse saveStation(StationRequest stationRequest) {
        isSameNameStation(stationRequest.getName());
        Station station = stationDao.insert(stationRequest.toStation());
        return StationResponse.of(station);
    }

    public Station findStationById(Long id) {
        return stationDao.findById(id);
    }

    public List<StationResponse> findAllStationResponses() {
        List<Station> stations = stationDao.findAll();

        return stations.stream()
                .map(StationResponse::of)
                .collect(Collectors.toList());
    }

    public void deleteStationById(Long id) {
        stationDao.deleteById(id);
    }

    private void isSameNameStation(String name){
        if(stationDao.countByName(name)!=NOT_DUPLICATED){
            throw new DuplicateStationNameException();
        }
    }
}
