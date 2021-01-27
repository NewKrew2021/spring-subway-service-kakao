package subway.auth.application;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exceptions.AuthorizationException;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public TokenResponse createToken(TokenRequest request) {
        if (!userExists(request.getEmail(), request.getPassword())) {
            throw new AuthorizationException("이메일과 패스워드가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(request.getEmail());
        return new TokenResponse(token);
    }

    private boolean userExists(String email, String password) {
        Member member;

        try {
            member = memberDao.findByEmail(email);
            return member.getEmail().equals(email) && member.getPassword().equals(password);
        } catch (DataAccessException e) {
            throw new AuthorizationException(e.getMessage());
        }
    }
}
