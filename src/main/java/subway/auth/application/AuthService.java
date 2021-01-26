package subway.auth.application;

import org.springframework.stereotype.Service;
import subway.auth.exception.InvalidTokenException;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;

@Service
public class AuthService {
    private final MemberDao memberDao;
    private JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    public boolean checkInvalidMember(String email, String password) {
        return !isMember(memberDao.findByEmail(email), password);
    }

    private boolean isMember(Member member, String password) {
        return member != null && member.getPassword().equals(password);
    }

    public Member checkInvalidToken(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new InvalidTokenException("인증토큰이 맞지않습니다.");
        }
        return memberDao.findByEmail(jwtTokenProvider.getPayload(accessToken));
    }
}
