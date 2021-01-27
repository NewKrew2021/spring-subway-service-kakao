package subway.favorite.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.line.exception.InvalidStationIdException;
import subway.station.dao.StationDao;
import subway.station.domain.Station;

import java.util.List;

@Service
public class FavoriteService {

    FavoriteDao favoriteDao;
    StationDao stationDao;

    @Autowired
    public FavoriteService(FavoriteDao favoriteDao, StationDao stationDao) {
        this.favoriteDao = favoriteDao;
        this.stationDao = stationDao;
    }

    public Favorite createFavorite(Long memberId, Long sourceStationId, Long targetStationId) {
        Station sourceStation = getStation(sourceStationId);
        Station targetStation = getStation(targetStationId);

        Favorite result = favoriteDao.insert(memberId, sourceStationId, targetStationId);

        return new Favorite(
                result.getId(),
                result.getMemberId(),
                sourceStation,
                targetStation);
    }

    private Station getStation(Long id) {
        try {
            return stationDao.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidStationIdException(id);
        }
    }

    public List<Favorite> findFavoritesByMemberId(Long memberId) {
        return favoriteDao.findByMemberId(memberId);
    }

    public void deleteFavorite(Long id) {
        favoriteDao.deleteById(id);
    }
}
