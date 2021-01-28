package subway.favorite.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import subway.exceptions.AuthorizationException;
import subway.exceptions.FavoriteDuplicateException;
import subway.exceptions.FavoriteSameArgumentException;
import subway.exceptions.InvalidRequestException;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;

import java.util.List;

@Service
public class FavoriteService {
    private final FavoriteDao favoriteDao;

    public FavoriteService(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    public long insert(long memberId, FavoriteRequest favoriteRequest) {
        Favorite favorite = new Favorite(memberId, favoriteRequest.getSource(), favoriteRequest.getTarget());

        if (favorite.getSourceStationId() == favorite.getTargetStationId()) {
            throw new FavoriteSameArgumentException("출발지와 도착지는 달라야 합니다.");
        }

        if (favoriteDao.favoriteExists(favorite)) {
            throw new FavoriteDuplicateException("이미 등록되어 있는 즐겨찾기 입니다.");
        }

        return favoriteDao.insert(favorite);
    }

    public List<FavoriteResponse> find(long memberId) {
        return favoriteDao.findFavoriteResponsesByMemberId(memberId);
    }

    public void delete(long favoriteId) {
        favoriteDao.deleteById(favoriteId);
    }

    public void checkValidRequest(FavoriteRequest favoriteRequest) {
        if (favoriteRequest == null) {
            throw new InvalidRequestException("request body에 정보가 없습니다.");
        }
        favoriteRequest.checkValidation();
    }

    public void checkValidUser(Long loginId, long favoriteId) {
        long memberId;
        try {
            memberId = favoriteDao.findById(favoriteId).getMemberId();
        } catch (DataAccessException e) {
            throw new InvalidRequestException("존재하지 않는 즐겨찾기 입니다.");
        }
        if (memberId != loginId) {
            throw new AuthorizationException("해당 즐겨찾기에 접근할 수 없는 사용자 입니다.");
        }
    }
}
