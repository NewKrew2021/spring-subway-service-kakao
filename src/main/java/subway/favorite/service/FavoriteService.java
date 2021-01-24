package subway.favorite.service;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteResponse;
import subway.station.dao.StationDao;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

@Service
public class FavoriteService {
    private FavoriteDao favoriteDao;
    private StationDao stationDao;

    public FavoriteService(FavoriteDao favoriteDao, StationDao stationDao){
        this.favoriteDao = favoriteDao;
        this.stationDao = stationDao;
    }

    public FavoriteResponse createFavorite(Long id, Long sourceId, Long targetId){
        Station sourceStation = stationDao.findById(sourceId);
        Station targetStation = stationDao.findById(targetId);
        favoriteDao.insert(id, sourceId, targetId);

        return  new FavoriteResponse(id, StationResponse.of(sourceStation), StationResponse.of(targetStation));
    }

    public Favorite getFavorite(Long memberId) {
        return favoriteDao.findByUser(memberId);
    }

    public void deleteFavorite(Long id) {
        favoriteDao.deleteFavorite(id);
    }
}
