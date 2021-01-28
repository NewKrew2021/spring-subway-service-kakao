package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;
import subway.station.dao.StationDao;
import subway.station.domain.Station;

import java.util.List;

@Service
public class FavoriteService {
    private FavoriteDao favoriteDao;
    private StationDao stationDao;
    private MemberDao memberDao;

    public FavoriteService(FavoriteDao favoriteDao, StationDao stationDao, MemberDao memberDao) {
        this.favoriteDao = favoriteDao;
        this.stationDao = stationDao;
        this.memberDao = memberDao;
    }

    public Favorite createFavorite(Long memberId, Long sourceId, Long targetId) {
        Member member = memberDao.findById(memberId);
        Station sourceStation = stationDao.findById(sourceId);
        Station targetStation = stationDao.findById(targetId);
        Long id = favoriteDao.insert(memberId, sourceId, targetId);
        return new Favorite(id, member, sourceStation, targetStation);
    }

    public List<Favorite> getFavorites(Long memberId) {
        return favoriteDao.findByUser(memberId);
    }

    public void deleteFavorite(Long id) {
        favoriteDao.deleteFavorite(id);
    }

}
