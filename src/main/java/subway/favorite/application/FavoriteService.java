package subway.favorite.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.station.application.StationService;
import subway.station.domain.Station;

import java.util.List;

@Service
public class FavoriteService {

    private FavoriteDao favoriteDao;
    private StationService stationService;

    @Autowired
    public FavoriteService(FavoriteDao favoriteDao, StationService stationService) {
        this.favoriteDao = favoriteDao;
        this.stationService = stationService;

    }

    public FavoriteResponse createFavorite(Long memberId, FavoriteRequest request) {
        Station sourceStation = stationService.findStationById(request.getSource());
        Station targetStation = stationService.findStationById(request.getTarget());

        Favorite favorite = favoriteDao.insert(new Favorite(memberId, sourceStation, targetStation));
        return FavoriteResponse.of(favorite);
    }

    public List<FavoriteResponse> findByMemberId(Long memberId) {
        return FavoriteResponse.listOf(favoriteDao.findByMemberId(memberId));
    }

    public void deleteByIdMemberAndId(Long id, Long memberId) {
        favoriteDao.deleteByIdMemberAndId(id, memberId);
    }
}
