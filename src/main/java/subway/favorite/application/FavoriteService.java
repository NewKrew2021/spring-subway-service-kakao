package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.member.domain.LoginMember;
import subway.station.dao.StationDao;
import subway.station.domain.Station;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {
    private final FavoriteDao favoriteDao;
    private final StationDao stationDao;

    public FavoriteService(FavoriteDao favoriteDao, StationDao stationDao) {
        this.favoriteDao = favoriteDao;
        this.stationDao = stationDao;
    }

    public FavoriteResponse saveFavorite(LoginMember loginMember, FavoriteRequest request) {
        Station sourceStation = stationDao.findById(request.getSource());
        Station targetStation = stationDao.findById(request.getTarget());
        Favorite favorite = favoriteDao.insert(new Favorite(loginMember.getId(), sourceStation, targetStation));
        return FavoriteResponse.of(favorite);
    }

    public List<FavoriteResponse> findFavoriteByMemberId(LoginMember loginMember) {
        List<Favorite> favorites = favoriteDao.findById(loginMember.getId());
        return favorites.stream()
                .map(FavoriteResponse::of)
                .collect(Collectors.toList());
    }

    public void deleteFavoriteById(LoginMember loginMember, Long favoriteId) {
        favoriteDao.deleteFavoriteById(loginMember.getId(), favoriteId);
    }
}
