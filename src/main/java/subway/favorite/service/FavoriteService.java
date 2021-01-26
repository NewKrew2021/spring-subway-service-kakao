package subway.favorite.service;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteResponse;
import subway.station.application.StationService;
import subway.station.dao.StationDao;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {
    private FavoriteDao favoriteDao;
    private StationDao stationDao;

    public FavoriteService(FavoriteDao favoriteDao, StationDao stationDao){
        this.favoriteDao = favoriteDao;
        this.stationDao = stationDao;
    }

    public FavoriteResponse createFavorite(Long id, Long sourceId, Long targetId){
        Station sourceStation = stationDao.findById(sourceId);
        Station targetStation = stationDao.findById(targetId);
        favoriteDao.insert(id, sourceId, targetId);

        return  new FavoriteResponse(id, StationResponse.of(sourceStation), StationResponse.of(targetStation));
    }

    public List<FavoriteResponse> getFavorite(Long memberId) {
        List<Favorite> favorites = favoriteDao.findByUser(memberId);
        return favorites.stream()
                .map(favorite -> getFavoriteResponse(favorite,
                                stationDao.findById(favorite.getSourceStationId()),
                                stationDao.findById(favorite.getTargetStationId())))
                .collect(Collectors.toList());
    }

    private FavoriteResponse getFavoriteResponse(Favorite favorite, Station source, Station target){
        return new FavoriteResponse(favorite.getId(),
                new StationResponse(source.getId(), source.getName()),
                new StationResponse(target.getId(), target.getName()));

    }

    public void deleteFavorite(Long id) {
        favoriteDao.deleteFavorite(id);
    }
}
