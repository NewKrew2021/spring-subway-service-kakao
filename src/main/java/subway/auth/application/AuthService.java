package subway.auth.application;

import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.JwtTokenProvider;
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
            throw new IllegalArgumentException();
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

    public Member getMemberByToken(String token){
        return memberDao.findByEmail(jwtTokenProvider.getPayload(token));
    }
}
