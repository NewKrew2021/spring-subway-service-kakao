package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.member.domain.LoginMember;
import subway.station.application.StationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {
    private final FavoriteDao favoriteDao;
    private final StationService stationService;

    public FavoriteService(FavoriteDao favoriteDao, StationService stationService) {
        this.favoriteDao = favoriteDao;
        this.stationService = stationService;
    }

    public Favorite saveFavorite(LoginMember loginMember, FavoriteRequest favoriteRequest) {
        Favorite favorite = new Favorite(
                loginMember.getId(),
                stationService.findStationById(favoriteRequest.getSource()),
                stationService.findStationById(favoriteRequest.getTarget())
        );
        return favoriteDao.saveFavorite(favorite);
    }

    public List<FavoriteResponse> findAllFavorites(LoginMember loginMember) {
        return favoriteDao.findAllByMemberId(loginMember.getId()).stream()
                .map(FavoriteResponse::of)
                .collect(Collectors.toList());
    }

    public void deleteFavorite(LoginMember loginMember, Long favoriteId) {
        favoriteDao.deleteFavoriteById(loginMember.getId(), favoriteId);
    }
}
