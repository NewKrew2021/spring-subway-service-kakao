package subway.favorite.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.auth.exception.InvalidTokenException;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.favorite.exception.FavoriteNotFoundException;
import subway.member.domain.LoginMember;
import subway.station.application.StationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {
    private final FavoriteDao favoriteDao;
    private final StationService stationService;

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
        throw new FavoriteNotFoundException();
    }

    public List<FavoriteResponse> findFavoriteResponses(Long memberId) {
        return favoriteDao.getFavorite(memberId).stream()
                .map(FavoriteResponse::of)
                .collect(Collectors.toList());
    }

    public void deleteFavoriteById(LoginMember loginMember, Long id) {
        Favorite favorite = favoriteDao.getFavoriteById(id).orElseThrow(FavoriteNotFoundException::new);
        if (!favorite.getId().equals(loginMember.getId())) {
            throw new InvalidTokenException();
        }
        favoriteDao.deleteById(id);
    }
}
