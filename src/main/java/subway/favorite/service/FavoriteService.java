package subway.favorite.service;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;

@Service
public class FavoriteService {

    private FavoriteDao favoriteDao;

    public FavoriteService(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    public void insertFavorite(FavoriteRequest favoriteRequest, Long memberId) {
        favoriteDao.save(favoriteRequest.getSource(), favoriteRequest.getTarget(), memberId);
    }

    public FavoriteResponse showFavoriteByMemberId(Long id) {
        Favorite favorite = favoriteDao.findById(id);
        return new FavoriteResponse(favorite.getId(), favorite.getSource(), favorite.getTarget());
    }

    public void deleteFavoriteById(Long memberId, Long favoriteId) {
        favoriteDao.deleteById(memberId, favoriteId);
    }
}
