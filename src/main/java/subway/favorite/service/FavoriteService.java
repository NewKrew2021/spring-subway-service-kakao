package subway.favorite.service;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.member.dao.MemberDao;
import subway.station.application.StationService;
import subway.station.domain.Station;

import java.util.List;

@Service
public class FavoriteService {
    private FavoriteDao favoriteDao;
    private StationService stationService;
    private MemberDao memberDao;

    public FavoriteService(FavoriteDao favoriteDao, StationService stationService, MemberDao memberDao){
        this.favoriteDao = favoriteDao;
        this.stationService = stationService;
        this.memberDao = memberDao;
    }

    public Favorite createFavorite(Long id, Long sourceId, Long targetId){
        Station sourceStation = stationService.findStationById(sourceId);
        Station targetStation = stationService.findStationById(targetId);
        Long favoriteId = favoriteDao.insert(id, sourceId, targetId);

        return new Favorite(favoriteId, memberDao.findById(id), sourceStation, targetStation);
    }

    public List<Favorite> getFavorite(Long memberId) {
        return favoriteDao.findByUser(memberId);
    }

    public void deleteFavorite(Long id) {
        favoriteDao.deleteFavorite(id);
    }
}
