package subway.auth.application;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exception.InvalidTokenException;
import subway.exception.LoginFailException;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;

import java.util.Optional;

@Service
public class AuthService {
    private final MemberDao memberDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse createToken(TokenRequest tokenRequest){
        if(!checkEmailValidation(tokenRequest.getEmail(), tokenRequest.getPassword())){
            throw new LoginFailException();
        }
        String accessToken = jwtTokenProvider.createToken(tokenRequest.getEmail());
        return new TokenResponse(accessToken);
    }

    private boolean checkEmailValidation(String email, String password) {
        Optional<Member> member = memberDao.findByEmail(email);
        return member.map(value -> value.getPassword().equals(password)).orElse(false);
    }

    public Member getMemberByToken(String token){
        if (jwtTokenProvider.validateToken(token)) {
            return memberDao.findByEmail(jwtTokenProvider.getPayload(token)).orElseThrow(InvalidTokenException::new);
        }
        throw new InvalidTokenException();
    }
}
