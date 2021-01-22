package subway.auth.application;

import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exception.LoginFailException;
import subway.member.dao.MemberDao;
import subway.member.domain.LoginMember;
import subway.member.domain.Member;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public String login(TokenRequest tokenRequest) {
        Member member = memberDao.findByEmail(tokenRequest.getEmail());
        if (member.getPassword().equals(tokenRequest.getPassword())) {
            return jwtTokenProvider.createToken(member.getEmail());
        }
        throw new LoginFailException();
    }

    public LoginMember decode(String token) {
        String email;
        if(jwtTokenProvider.validateToken(token)){
            email = jwtTokenProvider.getPayload(token);
            Member member = memberDao.findByEmail(email);
            return new LoginMember(member.getId(), member.getEmail(), member.getAge());
        }
        return new LoginMember();
    }
}
