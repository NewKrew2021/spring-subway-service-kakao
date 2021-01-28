package subway.auth.application;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exception.AuthenticationFailException;
import subway.exception.LoginFailException;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;

@Service
public class AuthService {
    private JwtTokenProvider jwtTokenProvider;
    private MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public TokenResponse login(TokenRequest tokenRequest) {
        authenticate(tokenRequest);
        return createToken(tokenRequest);
    }

    private TokenResponse createToken(TokenRequest tokenRequest) {
        return new TokenResponse(jwtTokenProvider.createToken(tokenRequest.getEmail()));
    }

    private void authenticate(TokenRequest tokenRequest) {
        try {
            Member member = memberDao.findByEmail(tokenRequest.getEmail());
            member.checkValidPassword(tokenRequest.getPassword());
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new LoginFailException();
        }
    }

    public Member getMemberByToken(String token) {
        validateToken(token);
        try {
            return memberDao.findByEmail(jwtTokenProvider.getPayload(token));
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new AuthenticationFailException();
        }
    }

    private void validateToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthenticationFailException();
        }
    }
}
