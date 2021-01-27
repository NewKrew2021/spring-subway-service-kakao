package subway.auth.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exceptions.UnauthenticatedException;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    @Autowired
    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public TokenResponse createToken(TokenRequest request) {
        if (!userExists(request.getEmail(), request.getPassword())) {
            throw new UnauthenticatedException("Incorrect password");
        }

        String token = jwtTokenProvider.createToken(request.getEmail());
        return new TokenResponse(token);
    }

    private boolean userExists(String email, String password) {
        try {
            Member member = memberDao.findByEmail(email);
            return member.getEmail().equals(email) && member.getPassword().equals(password);
        } catch (DataAccessException e) {
            throw new UnauthenticatedException("Incorrect email or password\n" + e.getMessage());
        }
    }
}
