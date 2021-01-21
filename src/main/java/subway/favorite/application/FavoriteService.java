package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteResponse;
import subway.member.domain.LoginMember;
import subway.station.application.StationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteDao favoriteDao;
    private final StationService stationService;

    public FavoriteService(FavoriteDao favoriteDao, StationService stationService) {
        this.favoriteDao = favoriteDao;
        this.stationService = stationService;
    }

    public Favorite create(LoginMember loginMember, Long source, Long target) {
        return favoriteDao.insert(new Favorite(loginMember.getId(), source, target));
    }

    public List<FavoriteResponse> findByLoginMember(LoginMember loginMember) {
        List<Favorite> favorites = favoriteDao.findByUserId(loginMember.getId());
        return favorites.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private FavoriteResponse mapToResponse(Favorite favorite) {
        return new FavoriteResponse(
                favorite,
                stationService.findStationById(favorite.getSourceId()),
                stationService.findStationById(favorite.getTargetId())
        );
    }
}
