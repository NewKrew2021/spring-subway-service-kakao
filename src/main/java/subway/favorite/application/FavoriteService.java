package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.station.application.StationService;
import subway.station.domain.Station;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class FavoriteService {

    private final StationService stationService;
    private final FavoriteDao favoriteDao;

    public FavoriteService(StationService stationService, FavoriteDao favoriteDao) {
        this.stationService = stationService;
        this.favoriteDao = favoriteDao;
    }

    public FavoriteResponse createFavorite(FavoriteRequest request, long memberId) {
        Favorite favorite = favoriteDao.insert(new Favorite(memberId, request.getSource(), request.getTarget()));
        Station sourceStation = stationService.findStationById(favorite.getSourceStationId());
        Station targetStation = stationService.findStationById(favorite.getTargetStationId());
        return FavoriteResponse.of(favorite, sourceStation, targetStation);
    }

    public void deleteFavorite(long id) {
        favoriteDao.deleteById(id);
    }

    public List<FavoriteResponse> findFavorites(long memberId) {
        List<Favorite> favorites = favoriteDao.findByMemberId(memberId);
        return favorites.stream()
                .map(favorite -> {
                    Station sourceStation = stationService.findStationById(favorite.getSourceStationId());
                    Station targetStation = stationService.findStationById(favorite.getTargetStationId());
                    return FavoriteResponse.of(favorite, sourceStation, targetStation);
                })
                .collect(Collectors.toList());
    }
}
