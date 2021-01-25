package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.station.dao.StationDao;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

@Service
public class FavoriteService {

    private FavoriteDao favoriteDao;
    private StationDao stationDao;

    public FavoriteService(FavoriteDao favoriteDao, StationDao stationDao) {
        this.favoriteDao = favoriteDao;
        this.stationDao = stationDao;
    }

    public FavoriteResponse createFavorite(FavoriteRequest favoriteRequest) {
        Favorite insertFavorite = favoriteDao.insert(new Favorite(favoriteRequest.getSource(), favoriteRequest.getTarget()));
        Station sourceStation = stationDao.findById(favoriteRequest.getSource());
        Station targetStation = stationDao.findById(favoriteRequest.getTarget());
        StationResponse sourceStationResponse = new StationResponse(sourceStation.getId(), sourceStation.getName());
        StationResponse targetStationResponse = new StationResponse(targetStation.getId(), targetStation.getName());

        return new FavoriteResponse(insertFavorite.getId(),sourceStationResponse, targetStationResponse);
    }
}
