package subway.auth.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import subway.auth.exception.InvalidTokenException;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;
import subway.member.exception.InvalidMemberException;

import java.util.Optional;

@Service
public class AuthService {
    private final MemberDao memberDao;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    public void checkInvalidMember(String email, String password) {
        Optional<Member> member = memberDao.findByEmail(email);
        if (!isMember(password, member.orElseThrow(() -> new InvalidMemberException()))) {
            throw new InvalidMemberException();
        }
    }

    private boolean isMember(String password, Member member) {
        return member.isSamePassword(password);
    }

    public Member checkInvalidToken(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new InvalidTokenException();
        }
        Optional<Member> member = memberDao.findByEmail(jwtTokenProvider.getPayload(accessToken));
        return member.orElseThrow(() -> new InvalidMemberException());
    }
}
