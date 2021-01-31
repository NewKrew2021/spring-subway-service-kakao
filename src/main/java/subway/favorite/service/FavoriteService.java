package subway.favorite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteResponse;
import subway.favorite.exceptions.FavoriteDuplicateException;
import subway.favorite.exceptions.FavoriteSameArgumentException;
import subway.station.dao.StationDao;
import subway.station.domain.Station;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FavoriteService {
    private final StationDao stationDao;
    private final FavoriteDao favoriteDao;

    @Autowired
    public FavoriteService(StationDao stationDao, FavoriteDao favoriteDao) {
        this.stationDao = stationDao;
        this.favoriteDao = favoriteDao;
    }

    public long insert(Favorite favorite) {
        if (favorite.getSourceStationId() == favorite.getTargetStationId()) {
            throw new FavoriteSameArgumentException("즐겨찾기의 출발지와 목적지가 같습니다");
        }

        if (favoriteDao.favoriteExists(favorite)) {
            throw new FavoriteDuplicateException("동일한 즐겨찾기가 있습니다");
        }

        return favoriteDao.insert(favorite);
    }

    public List<FavoriteResponse> findFavoriteResponses(long memberId) {
        Map<Long, Station> stationMap = new HashMap<>();
        stationDao.findAll()
                .forEach(it -> stationMap.put(it.getId(), it));
        return favoriteDao.findFavoritesByMemberId(memberId)
                .stream()
                .map(it -> new FavoriteResponse(
                        it.getMemberId(),
                        stationMap.get(it.getSourceStationId()).toResponse(),
                        stationMap.get(it.getTargetStationId()).toResponse()))
                .collect(Collectors.toList());
    }

    public void delete(long memberId, long favoriteId) {
        favoriteDao.deleteById(memberId, favoriteId);
    }
}
