package subway.favorite.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.exceptions.FavoriteDuplicateException;
import subway.exceptions.FavoriteSameArgumentException;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.station.dao.StationDao;
import subway.station.domain.Station;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FavoriteService {
    private final FavoriteDao favoriteDao;
    private final StationDao stationDao;

    @Autowired
    public FavoriteService(FavoriteDao favoriteDao, StationDao stationDao) {
        this.favoriteDao = favoriteDao;
        this.stationDao = stationDao;
    }

    public long insert(long memberId, FavoriteRequest favoriteRequest) {
        Favorite favorite = new Favorite(memberId, favoriteRequest.getSource(), favoriteRequest.getTarget());

        checkIsCorrectFavorite(favorite);

        return favoriteDao.insert(favorite);
    }

    private void checkIsCorrectFavorite(Favorite favorite) {
        if (favorite.getSourceStationId() == favorite.getTargetStationId()) {
            throw new FavoriteSameArgumentException("Source and target stations cannot be equal");
        }

        if (favoriteDao.favoriteExists(favorite)) {
            throw new FavoriteDuplicateException("Member has already added this section");
        }
    }

    public List<FavoriteResponse> find(long memberId) {
        List<Long> stationIds = favoriteDao.findFavoritesByMemberId(memberId)
                .stream()
                .map(favorite -> Stream.of(favorite.getSourceStationId(), favorite.getTargetStationId()))
                .flatMapToLong(stations -> stations.mapToLong(Long::valueOf))
                .boxed()
                .collect(Collectors.toList());

        Map<Long, Station> stations = stationDao.getStationsByIds(stationIds);

        return favoriteDao.findFavoritesByMemberId(memberId)
                .stream()
                .map(favorite -> FavoriteResponse.of(favorite,
                        stations.get(favorite.getSourceStationId()),
                        stations.get(favorite.getTargetStationId())))
                .collect(Collectors.toList());
    }

    public void delete(long memberId, long favoriteId) {
        favoriteDao.deleteById(memberId, favoriteId);
    }
}
