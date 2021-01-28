package subway.auth.application;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.exception.AuthorizationException;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.dao.MemberDao;
import subway.member.domain.LoginMember;
import subway.member.domain.Member;

@Service
public class AuthService {

    private static final String INVALID_LOGIN_MESSAGE = "유효하지 않은 로그인 정보입니다.";
    private static final String INVALID_TOKEN_MESSAGE = "유효하지 않은 토큰입니다.";
    private static final String NO_EXIST_EMAIL_MESSAGE = "존재하지 않는 이메일입니다.";

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        Member member = findMemberByEmail(tokenRequest.getEmail());
        if (!checkValidLogin(member, tokenRequest)) {
            throw new AuthorizationException(INVALID_LOGIN_MESSAGE);
        }
        String accessToken = jwtTokenProvider.createToken(tokenRequest.getEmail());
        return new TokenResponse(accessToken);
    }

    private boolean checkValidLogin(Member member, TokenRequest tokenRequest) {
        return member.getEmail().equals(tokenRequest.getEmail())
                && member.getPassword().equals(tokenRequest.getPassword());
    }

    public LoginMember findMemberByToken(String token) throws AuthorizationException {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException(INVALID_TOKEN_MESSAGE);
        }
        try {
            String payload = jwtTokenProvider.getPayload(token);
            return LoginMember.of(findMemberByEmail(payload));
        } catch (MalformedJwtException e) {
            throw new AuthorizationException(INVALID_TOKEN_MESSAGE+" : "+e.getMessage());
        }
    }

    public Member findMemberByEmail(String email) {
        try {
            return memberDao.findByEmail(email);
        } catch (EmptyResultDataAccessException e) {
            throw new AuthorizationException(NO_EXIST_EMAIL_MESSAGE+" : "+e.getMessage());
        }
    }
}
