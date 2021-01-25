package subway.auth.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.auth.exception.InvalidTokenException;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;
import subway.member.exception.InvalidMemberException;

@Service
public class AuthService {
    private final MemberDao memberDao;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    public void checkInvalidMember(String email, String password) {
        Member member = memberDao.findByEmail(email);
        if (isMember(password, member)) {
            throw new InvalidMemberException();
        }
    }

    private boolean isMember(String password, Member member) {
        return member.validatePassword(password);
    }

    public Member checkInvalidToken(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new InvalidTokenException();
        }
        return memberDao.findByEmail(jwtTokenProvider.getPayload(accessToken));
    }
}
