package subway.favorite.service;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.member.domain.LoginMember;
import subway.station.dao.StationDao;
import subway.station.domain.Station;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteDao favoriteDao;
    private final StationDao stationDao;

    public FavoriteService(FavoriteDao favoriteDao, StationDao stationDao) {
        this.favoriteDao = favoriteDao;
        this.stationDao = stationDao;
    }

    public FavoriteResponse createFavorite(FavoriteRequest favoriteRequest, Long memberId) {
        Station sourceStation = stationDao.findById(favoriteRequest.getSource());
        Station targetStation = stationDao.findById(favoriteRequest.getTarget());

        Favorite insertFavorite = favoriteDao.insert(new Favorite(sourceStation, targetStation, memberId));

        return FavoriteResponse.of(insertFavorite);
    }

    public void deleteFavorite(LoginMember loginMember, Long id) {
        favoriteDao.findAllByUserId(loginMember.getId())
                .stream()
                .filter(favorite -> favorite.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("타인의 즐겨찾기는 삭제할 수 없습니다."));

        favoriteDao.delete(id);
    }

    public List<Favorite> findAllByUserId(Long userId) {
        return favoriteDao.findAllByUserId(userId);
    }
}
