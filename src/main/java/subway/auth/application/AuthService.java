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
        memberDao.findByEmail(tokenRequest.getEmail())
                .checkValidMember(new Member(tokenRequest.getEmail(), tokenRequest.getPassword()));
        return new TokenResponse(jwtTokenProvider.createToken(tokenRequest.getEmail()));
    }

    public Member getMemberByToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new IllegalArgumentException();
        }
        return memberDao.findByEmail(jwtTokenProvider.getPayload(token));
    }
}
