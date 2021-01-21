package subway.favorite.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.station.application.StationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {
    private FavoriteDao favoriteDao;
    private StationService stationService;

    @Autowired
    public FavoriteService(FavoriteDao favoriteDao, StationService stationService) {
        this.favoriteDao = favoriteDao;
        this.stationService = stationService;
    }


    public FavoriteResponse saveFavorite(Long memberId, FavoriteRequest favoriteRequest) {
        if (favoriteRequest.getSource() != null && favoriteRequest.getTarget() != null) {
            Favorite favorite = Favorite.of(
                    memberId,
                    stationService.findStationById(favoriteRequest.getSource()),
                    stationService.findStationById(favoriteRequest.getTarget()));
            return FavoriteResponse.of(favoriteDao.insert(favorite));
        }
        return null;
    }

    public List<FavoriteResponse> findFavoriteResponses(Long memberId) {
        return favoriteDao.getFavorite(memberId).stream()
                .map(FavoriteResponse::of)
                .collect(Collectors.toList());
    }

    public void deleteFavoriteById(Long id) {
        favoriteDao.deleteById(id);
    }
}
