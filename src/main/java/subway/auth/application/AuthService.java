package subway.auth.application;

import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.application.MemberService;
import subway.member.dao.MemberDao;
import subway.member.domain.LoginMember;
import subway.member.domain.Member;
import subway.member.dto.MemberResponse;

@Service
public class AuthService {
    private JwtTokenProvider jwtTokenProvider;
    private MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }
    public TokenResponse createToken(TokenRequest tokenRequest) {
        checkValidMember(memberDao.findByEmail(tokenRequest.getEmail()), tokenRequest);
        String token = jwtTokenProvider.createToken(tokenRequest.getEmail());
        return new TokenResponse(token);
    }

    private void checkValidMember(Member member, TokenRequest tokenRequest) {
        if (!member.getEmail().equals(tokenRequest.getEmail()) || !member.getPassword().equals(tokenRequest.getPassword())) {
            throw new IllegalArgumentException();
        }
    }

    public Member getPayload(String token) {
        if(!jwtTokenProvider.validateToken(token)){
            throw new IllegalArgumentException();
        }
        return memberDao.findByEmail(jwtTokenProvider.getPayload(token));
    }
}
