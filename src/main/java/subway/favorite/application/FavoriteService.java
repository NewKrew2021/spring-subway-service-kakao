package subway.favorite.application;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import subway.exception.AuthorizationException;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.station.dao.StationDao;
import subway.station.domain.Station;
import subway.station.dto.StationResponse;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteDao favoriteDao;
    private final StationDao stationDao;

    public FavoriteService(FavoriteDao favoriteDao, StationDao stationDao) {
        this.favoriteDao = favoriteDao;
        this.stationDao = stationDao;
    }

    public FavoriteResponse createFavorite(Long memberId, FavoriteRequest favoriteRequest) {
        Station source = stationDao.findById(favoriteRequest.getSource());
        Station target = stationDao.findById(favoriteRequest.getTarget());
        Favorite favorite = favoriteDao.insert(new Favorite(memberId, source, target));

        return FavoriteResponse.of(favorite.getId(), StationResponse.of(source), StationResponse.of(target));
    }

    public List<FavoriteResponse> searchFavorite(Long memberId) {
        List<Favorite> favorites = favoriteDao.findByMemberId(memberId);
        return favorites.stream()
                .map(favorite -> FavoriteResponse.of(favorite.getId(), StationResponse.of(favorite.getSource()), StationResponse.of(favorite.getTarget())))
                .collect(Collectors.toList());
    }

    public void deleteFavorite(Long memberId, Long id) {
        if (!memberHasFavorite(memberId, id)) {
            throw new InvalidParameterException("등록된 즐겨찾기가 존재하지 않습니다.");
        }

        favoriteDao.deleteById(id);
    }

    private boolean memberHasFavorite(Long memberId, Long id) {
        return favoriteDao.findMemberIdById(id).contains(memberId);
    }
}
