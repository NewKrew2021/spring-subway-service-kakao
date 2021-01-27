package subway.favorite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.favorite.exceptions.FavoriteDuplicateException;
import subway.favorite.exceptions.FavoriteNullRequestException;
import subway.favorite.exceptions.FavoriteSameArgumentException;

import java.util.List;

@Service
public class FavoriteService {
    private final FavoriteDao favoriteDao;

    @Autowired
    public FavoriteService(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    public long insert(long memberId, FavoriteRequest favoriteRequest) {
        if (favoriteRequest == null) {
            throw new FavoriteNullRequestException("favoriteRequest가 비어있습니다");
        }
        Favorite favorite = new Favorite(memberId, favoriteRequest.getSource(), favoriteRequest.getTarget());

        if (favorite.getSourceStationId() == favorite.getTargetStationId()) {
            throw new FavoriteSameArgumentException("즐겨찾기의 출발지와 목적지가 같습니다");
        }

        if (favoriteDao.favoriteExists(favorite)) {
            throw new FavoriteDuplicateException("동일한 즐겨찾기가 있습니다");
        }

        return favoriteDao.insert(favorite);
    }

    public List<FavoriteResponse> find(long memberId) {
        return favoriteDao.findFavoriteResponsesByMemberId(memberId);
    }

    public void delete(long memberId, long favoriteId) {
        favoriteDao.deleteById(memberId, favoriteId);
    }
}
