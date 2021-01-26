package subway.station.application;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.line.dao.SectionDao;
import subway.station.dao.StationDao;
import subway.station.domain.Station;
import subway.station.dto.StationRequest;
import subway.station.dto.StationResponse;
import subway.station.exception.StationException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {

    private static final String FAILED_DELETE_MESSAGE = "해당 역으로 구성된 구간이 존재합니다.";

    private StationDao stationDao;
    private SectionDao sectionDao;
    private FavoriteDao favoriteDao;

    public StationService(StationDao stationDao, SectionDao sectionDao, FavoriteDao favoriteDao) {
        this.stationDao = stationDao;
        this.sectionDao = sectionDao;
        this.favoriteDao = favoriteDao;
    }

    public StationResponse saveStation(StationRequest stationRequest) {
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
        if (sectionDao.findByStationId(id) > 0) {
            throw new StationException(FAILED_DELETE_MESSAGE);
        }
        favoriteDao.deleteFavoriteByStationId(id);
        stationDao.deleteById(id);
    }
}
