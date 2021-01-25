package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.member.domain.LoginMember;
import subway.station.application.StationService;
import subway.station.dto.StationResponse;

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

    public FavoriteResponse createFavorite(LoginMember loginMember, FavoriteRequest favoriteRequest) {
        Favorite favorite = favoriteDao.save(new Favorite(loginMember.getId(), favoriteRequest.getSource(), favoriteRequest.getTarget()));
        return new FavoriteResponse(favorite.getId(),
                StationResponse.of(stationService.findStationById(favorite.getSource())),
                StationResponse.of(stationService.findStationById(favorite.getTarget())));
    }

    public List<FavoriteResponse> findAll(LoginMember loginMember) {
        List<Favorite> favorites = favoriteDao.findAll(loginMember.getId());
        return favorites.stream().map(favorite -> new FavoriteResponse(favorite.getId(),
                StationResponse.of(stationService.findStationById(favorite.getSource())),
                StationResponse.of(stationService.findStationById(favorite.getTarget())))).collect(Collectors.toList());
    }

    public void deleteFavorite(long favoriteId) {
        favoriteDao.deleteById(favoriteId);
    }
}
