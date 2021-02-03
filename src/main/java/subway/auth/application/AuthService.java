package subway.auth.application;

import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exception.InvalidTokenException;
import subway.exception.LoginFailException;
import subway.member.dao.MemberDao;
import subway.member.domain.LoginMember;
import subway.member.domain.Member;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final MemberDao memberDao;
    private final JwtTokenProvider jwtTokenProvider;
    private Map<String, Member> cache = new HashMap<>();

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
        Member member = memberDao.findByEmail(email);
        return member != null && member.getPassword().equals(password);
    }
    public String getPayLoad(String token){
        return jwtTokenProvider.getPayload(token);
    }

    public void validateToken(String accessToken) {
        if(accessToken == null || !jwtTokenProvider.validateToken(accessToken))
            throw new InvalidTokenException();
    }

    public void putLoginMember(String key, Member value){
        validateToken(key);
        cache.put(key, value);
    }

    public Member getLoginMember(String key){
        return cache.containsKey(key) ? cache.get(key) : null;
    }
}
