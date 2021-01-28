package subway.auth.application;

import org.springframework.stereotype.Service;
import subway.auth.dto.TokenResponse;
import subway.auth.exception.UnAuthorizedException;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.domain.LoginMember;

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

    public LoginMember getLoginMemberInToken(String token) {
        if(token == null) {
            throw new UnAuthorizedException();
        }
        if(!tokenProvider.validateToken(token)){
            throw new UnAuthorizedException();
        }

        LoginMember loginMember = tokenMap.get(token);

        if(loginMember == null) {
            throw new UnAuthorizedException();
        }

        return loginMember;
    }
}
