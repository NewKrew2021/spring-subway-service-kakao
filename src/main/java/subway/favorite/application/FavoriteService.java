package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.member.domain.LoginMember;
import subway.station.dao.StationDao;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {
    private FavoriteDao favoriteDao;
    private StationDao stationDao;

    public FavoriteService(FavoriteDao favoriteDao, StationDao stationDao) {
        this.favoriteDao = favoriteDao;
        this.stationDao = stationDao;
    }

    public FavoriteResponse create(LoginMember loginMember, FavoriteRequest favoriteRequest) {
        Favorite favorite = favoriteDao.insert(new Favorite(favoriteRequest.getSource(), favoriteRequest.getTarget(), loginMember.getId()));
        return new FavoriteResponse(favorite.getId(), stationDao.findById(favorite.getSourceStationId()),
                stationDao.findById(favorite.getTargetStationId()));
    }

    public List<FavoriteResponse> findAll(LoginMember loginMember) {
        return favoriteDao.findAll(loginMember.getId()).stream()
                .map(favorite -> new FavoriteResponse(favorite.getId(), stationDao.findById(favorite.getSourceStationId()),
                        stationDao.findById(favorite.getTargetStationId())))
                .collect(Collectors.toList());
    }

    public void deleteByIdAndMemberId(LoginMember loginMember, Long favoriteId) {
        favoriteDao.deleteByIdAndMemberId(loginMember.getId(), favoriteId);
    }
}
