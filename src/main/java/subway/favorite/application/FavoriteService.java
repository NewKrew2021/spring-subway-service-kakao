package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;

import java.util.List;

@Service
public class FavoriteService {

    FavoriteDao favoriteDao;

    public FavoriteService(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    public Favorite createFavorite(Long memberId, Long sourceStationId, Long targetStationId) {
        return favoriteDao.insert(new Favorite(memberId, sourceStationId, targetStationId));
    }

    public List<Favorite> findAllFavorites(Long memberId) {
        return favoriteDao.findAll(memberId);
    }

    public void deleteFavorite(Long id) {
        favoriteDao.deleteById(id);
    }
}
