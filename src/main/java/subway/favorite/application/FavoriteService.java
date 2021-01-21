package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.auth.domain.AuthenticationPrincipal;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.member.domain.LoginMember;

@Service
public class FavoriteService {

    private final FavoriteDao favoriteDao;

    public FavoriteService(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    public Favorite create(LoginMember loginMember, Long source, Long target) {
        return favoriteDao.insert(new Favorite(loginMember.getId(), source, target));
    }
}
