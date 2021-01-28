package subway.auth.application;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.exception.InvalidLoginException;
import subway.auth.infrastructure.JwtTokenProvider;
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

    private boolean checkValidLogin(String email, String password) {
        try {
            return memberDao.findByEmail(email)
                    .getPassword()
                    .equals(password);
        } catch (EmptyResultDataAccessException erdae) {
            return false;
        }
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        if (!checkValidLogin(tokenRequest.getEmail(), tokenRequest.getPassword())) {
            throw new InvalidLoginException();
        }
        String accessToken = jwtTokenProvider.createToken(tokenRequest.getEmail());
        return new TokenResponse(accessToken);
    }

    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    public Member findMemberByToken(String token) {
        String email = jwtTokenProvider.getPayload(token);
        return memberDao.findByEmail(email);
    }
}
