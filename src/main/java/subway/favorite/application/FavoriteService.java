package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;

@Service
public class FavoriteService {

    FavoriteDao favoriteDao;

    public FavoriteService(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    public Favorite createFavorite(Long memberId, Long sourceStationId, Long targetStationId) {
        return favoriteDao.insert(new Favorite(memberId, sourceStationId, targetStationId));
    }
}
