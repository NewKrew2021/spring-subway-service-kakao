package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.member.domain.LoginMember;
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

    public Favorite saveFavorite(LoginMember member, FavoriteRequest request) {
        Favorite favorite = new Favorite(member,
                stationDao.findById(request.getSource()),
                stationDao.findById(request.getTarget()));
        return favoriteDao.insert(favorite);
    }

    public List<Favorite> getFavorites(LoginMember loginMember) {
        return favoriteDao.findByMemberId(loginMember.getId());
    }

    public void deleteFavorite(Long id) {
        favoriteDao.deleteById(id);
    }
}
