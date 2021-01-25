package subway.auth.application;

import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.dao.MemberDao;
import subway.member.domain.LoginMember;
import subway.member.domain.Member;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public TokenResponse createToken(TokenRequest tokenRequest) {
        String accessToken = jwtTokenProvider.createToken(tokenRequest.getEmail());
        return new TokenResponse(accessToken);
    }

    public LoginMember findMemberByToken(String token) {
        String email = jwtTokenProvider.getPayload(token);
        Member member = memberDao.findByEmail(email);
        return new LoginMember(member.getId(), member.getEmail(), member.getAge());
    }

    public boolean validToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

}
