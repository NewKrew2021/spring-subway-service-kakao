package subway.auth.application;

import org.springframework.stereotype.Service;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exception.InvalidMemberException;
import subway.exception.InvalidTokenException;
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

    public TokenResponse createAuth(String email, String password) {
        if(!memberDao.findByEmail(email).getPassword().equals(password)){
            throw new InvalidMemberException();
        }
        String token = jwtTokenProvider.createToken(email);
        return new TokenResponse(token);
    }

    public Member getMemberByToken(String token) {
        if(jwtTokenProvider.validateToken(token)) {
            String payLoad = jwtTokenProvider.getPayload(token);
            Member member = memberDao.findByEmail(payLoad);
            return member;
        }
        throw new InvalidTokenException();
    }

}


