package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.station.dao.StationDao;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteDao favoriteDao;
    private final StationDao stationDao;

    public FavoriteService(FavoriteDao favoriteDao, StationDao stationDao) {
        this.favoriteDao = favoriteDao;
        this.stationDao = stationDao;
    }

    public FavoriteResponse createFavorite(Long sourceId, Long targetId, Long memberId) {
        Station sourceStation = stationDao.findById(sourceId);
        Station targetStation = stationDao.findById(targetId);

        Favorite insertFavorite = favoriteDao.insert(new Favorite(sourceStation, targetStation, memberId));

        return new FavoriteResponse(insertFavorite.getId(),
                StationResponse.of(sourceStation), StationResponse.of(targetStation));
    }

    public void deleteFavorite(Long id) {
        favoriteDao.delete(id);
    }

    public List<Favorite> findAllByUserId(Long userId) {
        return favoriteDao.findAllByUserId(userId);
    }
}
