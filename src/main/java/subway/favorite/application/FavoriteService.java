package subway.favorite.application;

import org.springframework.stereotype.Service;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.favorite.dao.FavoriteDao;
import subway.favorite.domain.Favorite;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;

@Service
public class FavoriteService {
    private final FavoriteDao favoriteDao;
    private final MemberDao memberDao;
    private final JwtTokenProvider jwtTokenProvider;

    public FavoriteService(FavoriteDao favoriteDao, JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.favoriteDao = favoriteDao;
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void save(String token, Long source, Long target) {
        Member member = memberDao.findByEmail(jwtTokenProvider.getPayload(token));
        favoriteDao.save(new Favorite(member.getId(), source, target));
    }
}
