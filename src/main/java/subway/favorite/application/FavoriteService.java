package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exception.InvalidTokenException;
import subway.exception.RequestPermissionDeniedException;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteResponse;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;
import subway.station.dao.StationDao;
import subway.station.dto.StationResponse;

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

    public Favorite save(Long memberId, Long source, Long target) {
        return favoriteDao.save(new Favorite(memberId, source, target));
    }

    public List<Favorite> getFavorites(Long memberId) {
        return favoriteDao.findAll(memberId);
    }

    public List<FavoriteResponse> convertFavoriteResponse(List<Favorite> favorites) {
        return favorites.stream()
                .map(favorite ->
                    new FavoriteResponse(favorite.getId(),
                            StationResponse.of(stationDao.findById(favorite.getSourceId())),
                            StationResponse.of(stationDao.findById(favorite.getTargetId()))))
                .collect(Collectors.toList());
    }

    public void deleteFavorites(Long favoriteId, Long expectedMemberId) {
        Favorite favorite = favoriteDao.findById(favoriteId);
        if(!favorite.getMemberId().equals(expectedMemberId)){
            throw new RequestPermissionDeniedException();
        }
        favoriteDao.deleteById(favoriteId);
    }
}
