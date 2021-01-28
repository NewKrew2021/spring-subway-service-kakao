package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.exception.InvalidMemberException;
import subway.exception.StationNotFoundException;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;
import subway.station.dao.StationDao;
import subway.station.domain.Station;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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
        Optional<Member> member = memberDao.findById(memberId);
        Optional<Station> sourceStation = stationDao.findById(sourceId);
        Optional<Station> targetStation = stationDao.findById(targetId);
        validMemberFindById(member);
        validStationFindById(sourceStation);
        validStationFindById(targetStation);
        Long id = favoriteDao.insert(memberId, sourceId, targetId);
        return new Favorite(id, member.get(), sourceStation.get(), targetStation.get());
    }

    private void validMemberFindById(Optional<Member> member) {
        if (!member.isPresent()) {
            throw new InvalidMemberException();
        }
    }

    private void validStationFindById(Optional<Station> station) {
        if (!station.isPresent()) {
            throw new StationNotFoundException("해당 상행점 혹은 하행점을 찾을 수 없습니다.");
        }
    }

    public List<Favorite> getFavorites(Long memberId) {
        return favoriteDao.findByUser(memberId);
    }

    public void deleteFavorite(Long id) {
        favoriteDao.deleteFavorite(id);
    }

}
