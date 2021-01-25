package subway.auth.application;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exception.AuthorizationFailException;
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

    public TokenResponse createToken(TokenRequest tokenRequest) {
        try {
            memberDao.findByEmail(tokenRequest.getEmail())
                    .checkValidMember(new Member(tokenRequest.getEmail(), tokenRequest.getPassword()));
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new LoginFailException();
        }
        return new TokenResponse(jwtTokenProvider.createToken(tokenRequest.getEmail()));
    }

    public Member getMemberByToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationFailException();
        }
        try {
            return memberDao.findByEmail(jwtTokenProvider.getPayload(token));
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new AuthorizationFailException();
        }
    }
}
