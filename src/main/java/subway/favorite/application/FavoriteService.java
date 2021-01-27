package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.exceptions.NotExistsDataException;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteResponse;
import subway.member.domain.LoginMember;
import subway.station.application.StationService;
import subway.station.dto.StationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {
    public static final int NO_UPDATED_ROW = 0;
    private FavoriteDao favoriteDao;
    private StationService stationService;

    public FavoriteService(FavoriteDao favoriteDao, StationService stationService) {
        this.favoriteDao = favoriteDao;
        this.stationService = stationService;
    }

    public FavoriteResponse createFavorite(LoginMember loginMember, Long source, Long target) {
        Favorite favorite = favoriteDao.save(new Favorite(loginMember.getId(), source, target))
                .orElseThrow(() -> new NotExistsDataException("즐겨찾기 저장에 실패했습니다."));
        return new FavoriteResponse(favorite.getId(),
                StationResponse.of(stationService.findStationById(favorite.getSource())),
                StationResponse.of(stationService.findStationById(favorite.getTarget())));
    }

    public List<FavoriteResponse> findAll(LoginMember loginMember) {
        List<Favorite> favorites = favoriteDao.findAll(loginMember.getId());
        return favorites.stream().map(favorite -> new FavoriteResponse(favorite.getId(),
                StationResponse.of(stationService.findStationById(favorite.getSource())),
                StationResponse.of(stationService.findStationById(favorite.getTarget())))).collect(Collectors.toList());
    }

    public void deleteFavorite(long favoriteId) {
        if(favoriteDao.deleteById(favoriteId) == NO_UPDATED_ROW) {
            throw new NotExistsDataException("삭제하려는 즐겨찾기가 존재하지 않습니다.");
        }
    }
}
