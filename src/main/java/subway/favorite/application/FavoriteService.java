package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.station.dao.StationDao;

import java.util.List;

@Service
public class FavoriteService {

    FavoriteDao favoriteDao;
    StationDao stationDao;

    public FavoriteService(FavoriteDao favoriteDao, StationDao stationDao) {
        this.favoriteDao = favoriteDao;
        this.stationDao = stationDao;
    }

    public Favorite createFavorite(Long memberId, Long sourceStationId, Long targetStationId) {
        Favorite result = favoriteDao.insert(memberId, sourceStationId, targetStationId);

        return new Favorite(
                result.getId(),
                result.getMemberId(),
                stationDao.findById(result.getSourceStationId()),
                stationDao.findById(result.getTargetStationId()));
    }

    public List<Favorite> findFavoritesByMemberId(Long memberId) {
        return favoriteDao.findByMemberId(memberId);
    }

    public void deleteFavorite(Long id) {
        favoriteDao.deleteById(id);
    }
}
