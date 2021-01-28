package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exception.InvalidTokenException;
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
    private final MemberDao memberDao;
    private final StationDao stationDao;
    private final JwtTokenProvider jwtTokenProvider;

    public FavoriteService(FavoriteDao favoriteDao, MemberDao memberDao, StationDao stationDao, JwtTokenProvider jwtTokenProvider) {
        this.favoriteDao = favoriteDao;
        this.memberDao = memberDao;
        this.stationDao = stationDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Favorite save(String email, Long source, Long target) {
        Member member = memberDao.findByEmail(email);
        return favoriteDao.save(new Favorite(member.getId(), source, target));
    }

    public List<Favorite> getFavorites(String email) {
        Member member = memberDao.findByEmail(email);
        return favoriteDao.findAll(member.getId());
    }

    public List<FavoriteResponse> convertFavoriteResponse(List<Favorite> favorites) {
        return favorites.stream()
                .map(favorite ->
                    new FavoriteResponse(favorite.getId(),
                            StationResponse.of(stationDao.findById(favorite.getSourceId())),
                            StationResponse.of(stationDao.findById(favorite.getTargetId()))))
                .collect(Collectors.toList());
    }

    public void deleteFavorites(String email, Long favoriteId) {
        if(!email.equals(memberDao.findById(favoriteDao.findById(favoriteId).getMemberId()).getEmail())){
            throw new InvalidTokenException();
        }

        favoriteDao.deleteById(favoriteId);
    }
}
