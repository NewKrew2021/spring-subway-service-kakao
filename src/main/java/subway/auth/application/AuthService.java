package subway.auth.application;

import org.springframework.stereotype.Service;
import subway.auth.dto.TokenResponse;
import subway.auth.exception.UnAuthorizedException;
import subway.auth.infrastructure.AuthorizationExtractor;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.domain.LoginMember;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private JwtTokenProvider tokenProvider;
    private Map<String, LoginMember> tokenMap = new HashMap<>();

    public AuthService(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public TokenResponse createTokenFor(LoginMember loginMember) {
        String token = tokenProvider.createToken(loginMember.getEmail());
        tokenMap.put(token, loginMember);
        return new TokenResponse(token);
    }

    public LoginMember validateToken(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        if(token == null) {
            throw new UnAuthorizedException();
        }

        LoginMember loginMember = tokenMap.get(token);

        if(loginMember == null) {
            throw new UnAuthorizedException();
        }

        return loginMember;
    }
}
