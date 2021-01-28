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

    private boolean checkValidLogin(StringBuilder errorMessageBuilder, String email, String password) {
        try {
            if(!memberDao.findByEmail(email)
                    .getPassword()
                    .equals(password)){
                errorMessageBuilder.append(InvalidLoginException.EMAIL_PASSWORD_MISMATCH);
                return false;
            }
        } catch (EmptyResultDataAccessException erdae) {
            errorMessageBuilder.append(InvalidLoginException.EMAIL_NOT_EXIST);
            return false;
        }
        return true;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        StringBuilder errorMessageBuilder = new StringBuilder();
        if (!checkValidLogin(errorMessageBuilder,
                tokenRequest.getEmail(), tokenRequest.getPassword())) {
            throw new InvalidLoginException(errorMessageBuilder.toString());
        }
        String accessToken = jwtTokenProvider.createToken(tokenRequest.getEmail());
        return TokenResponse.of(accessToken);
    }

    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    public Member findMemberByToken(String token) {
        String email = jwtTokenProvider.getPayload(token);
        return memberDao.findByEmail(email);
    }
}
