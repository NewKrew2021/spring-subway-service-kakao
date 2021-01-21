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
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        Member member = findMemberByEmail(tokenRequest.getEmail());
        if (!checkValidLogin(member, tokenRequest)) {
            throw new AuthorizationException("유효하지 않은 로그인 정보입니다.");
        }
        String accessToken = jwtTokenProvider.createToken(tokenRequest.getEmail());
        return new TokenResponse(accessToken);
    }

    private boolean checkValidLogin(Member member, TokenRequest tokenRequest) {
        return member.getEmail().equals(tokenRequest.getEmail())
                && member.getPassword().equals(tokenRequest.getPassword());
    }

    public LoginMember findMemberByToken(String token) {
        try {
            String payload = jwtTokenProvider.getPayload(token);
            return LoginMember.of(findMemberByEmail(payload));
        } catch (MalformedJwtException e) {
            throw new AuthorizationException("유효하지 않는 토큰입니다. : " + e.getMessage());
        }
    }

    public Member findMemberByEmail(String email) {
        try {
            return memberDao.findByEmail(email);
        } catch (EmptyResultDataAccessException e) {
            throw new AuthorizationException("존재하지 않는 이메일입니다. : " + e.getMessage());
        }
    }
}
