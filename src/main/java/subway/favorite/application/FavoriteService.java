package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.member.domain.LoginMember;
import subway.station.application.StationService;
import subway.station.domain.Station;

import java.util.List;

@Service
public class FavoriteService {

    private FavoriteDao favoriteDao;
    private StationService stationService;

    public FavoriteService(FavoriteDao favoriteDao, StationService stationService) {
        this.favoriteDao = favoriteDao;
        this.stationService = stationService;

    }

    public Favorite createFavorite(LoginMember loginMember, Long sourceId, Long targetId) {
        Station sourceStation = stationService.findStationById(sourceId);
        Station targetStation = stationService.findStationById(targetId);

        return favoriteDao.insert(new Favorite(loginMember, sourceStation, targetStation));
    }

    public List<Favorite> findByMemberId(Long memberId) {
        return favoriteDao.findByMemberId(memberId);
    }

    public void deleteByIdMemberAndId(Long id, Long memberId) {
        favoriteDao.deleteByIdMemberAndId(id, memberId);
    }
}
