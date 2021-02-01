package subway.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.auth.dto.TokenRequest;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exception.AuthorizationException;
import subway.member.dao.MemberDao;
import subway.member.domain.LoginMember;
import subway.member.domain.Member;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    @Transactional
    public String login(TokenRequest tokenRequest) {
        Member member = memberDao.findByEmail(tokenRequest.getEmail());
        validatePassword(tokenRequest.getPassword(), member.getPassword());
        return jwtTokenProvider.createToken(member.getEmail());
    }

    private void validatePassword(String stored, String given) {
        if(!given.equals(stored)) {
            throw new AuthorizationException("패스워드가 일치하지 않습니다.");
        }
    }

    @Transactional
    public LoginMember getLoginMember(String token) {
        validateToken(token);
        String email = jwtTokenProvider.getPayload(token);
        Member member = memberDao.findByEmail(email);
        return new LoginMember(member.getId(), member.getEmail(), member.getAge());
    }

    private void validateToken(String token) {
        if(!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException("잘못된 token입니다.");
        }
    }
}
